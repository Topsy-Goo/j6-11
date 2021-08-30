package ru.gb.antonov.j611.beans.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.gb.antonov.j611.beans.services.OurUserService;

@EnableWebSecurity  //< «в(ы)ключатель» правил безопасности в проекте
@RequiredArgsConstructor
@Slf4j  //< логгирование
@EnableGlobalMethodSecurity(prePostEnabled = true,  //< разрешает использование @PreAuthorize и @PostAuthorize
                            securedEnabled = true,  //< разрешает использование @Secured
                            jsr250Enabled = true)   //< разрешает использование @RoleAllowed
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final OurUserService ourUserService;

//Комментарий (2)
    //  более гибкий вариант:
    //@PreAuthorize ("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')") == нужны обе роли
    //@PreAuthorize ("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") == хотябы одна из ролей
    //@PreAuthorize ("!hasRole('ROLE_GUEST') and hasRole('ROLE_ADMIN')") == админ и НЕ гость
    //
    //  менее гибкий вариант (кажется, основан на static-правилах):
    //@Secured ({"ROLE_USER", "ROLE_ADMIN") == хотябы одна из ролей
    //
    //  @PostAuthorize - кажется, это отложенная авторизация (метод выполнится, но результаты будут доступны только после авторизации)
    //  также см. https://www.baeldung.com/spring-security-method-security

    @Override
    protected void configure (HttpSecurity httpSec) throws Exception
    {
        log.info("Dao Authentication Provider");

    //Мы можем защищать отдельные группы страниц:
        httpSec.authorizeRequests()
               .antMatchers("/exhibition").authenticated()    //< для авторизованных юзеров (ROLE_USER+)
               .antMatchers("/garden").hasAuthority("WALK")
               .antMatchers("/chat").hasAuthority("TALK")
               .antMatchers("/office").hasAnyRole("ADMIN", "SUPERADMIN")
               .antMatchers("/office/diningroom").hasAuthority("EAT")
               .antMatchers("/office/controlroom").hasAuthority("SLEEP")
               //.anyRequest().permitAll()    //< все остальные запросы доступны всем включая гостей
               // …Но мы не пойдём этим путём, чтобы не пустить гостей в служебную область, для которой забыли выставить права доступа. «Звёздочки» (вида /somearea/**) тоже под запретом по той же причине.
               // Явно указываем права доступа для общих областей.
               .antMatchers("/cafe").permitAll()
    //создание формы авторизации при авторизации:
               .and()
               .formLogin() //< метод отдаёт страницу; в Rest'е его не будет, т.к. там нам не нужна посторонняя страница, там обмениваются JSON'ами.
    //настройка действий для выхода пользователя из учётки:
               .and()
               .logout()
               .invalidateHttpSession(true)   //< true == закрыть сессию при выходе пользователя из системы
               .deleteCookies("JSESSIONID");  //< стираем JSID
    //Настройка управления сессиями:
               //.and()
               //.sessionManagement()
               //.maximumSessions(1)  < количество одновременных сессий для данного юзера
               //.maxSessionsPreventsLogin (true)  < отказ в регистрации, если кол-во сессий превысило указанный выше предел. (Умолчальное значение: false. Кажется, оно означает, что при каждой дополнительной авторизации сверх установленной выше нормы, одна из открытых ранее сессий будет закрываться. Похоже на принцип вытеснения, при котором предпочтение отдаётся новым сессиям.)
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()  {   return new BCryptPasswordEncoder();   }
    //NoOpPasswordEncoder (Deprecated) не кодирует пароль.

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setPasswordEncoder (passwordEncoder()); //< раз мы не можем расшифровать пароль, взятый из базы, то зашифруем присланный юзером пароль и сравним две абракадабры.
        dap.setUserDetailsService (ourUserService); //< дадим Auth-Provider'у инструмент для извлечения данных пользователя из базы.
        return dap;
    }
}
