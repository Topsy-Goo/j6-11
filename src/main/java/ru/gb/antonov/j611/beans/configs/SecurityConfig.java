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

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true,
                            securedEnabled = true,
                            jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final OurUserService ourUserService;

    @Override
    protected void configure (HttpSecurity httpSec) throws Exception
    {
        log.info("Dao Authentication Provider");

        httpSec.authorizeRequests()
               .antMatchers("/exhibition").authenticated()
               .antMatchers("/garden").hasAuthority("WALK")
               .antMatchers("/chat").hasAuthority("TALK")
               .antMatchers("/office").hasAnyRole("ADMIN", "SUPERADMIN")
               .antMatchers("/office/diningroom").hasAuthority("EAT")
               .antMatchers("/office/controlroom").hasAuthority("SLEEP")
               .antMatchers("/cafe").permitAll()
               .and()
               .formLogin()
               .and()
               .logout()
               .invalidateHttpSession(true)
               .deleteCookies("JSESSIONID");
               //.and()
               //.sessionManagement()
               //.maximumSessions(1)
               //.maxSessionsPreventsLogin (true)
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()  {   return new BCryptPasswordEncoder();   }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setPasswordEncoder (passwordEncoder());
        dap.setUserDetailsService (ourUserService);
        return dap;
    }
}
