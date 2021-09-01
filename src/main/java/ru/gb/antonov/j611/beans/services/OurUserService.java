package ru.gb.antonov.j611.beans.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.antonov.j611.beans.repos.OurUsersRepo;
import ru.gb.antonov.j611.entities.Action;
import ru.gb.antonov.j611.entities.OurUser;
import ru.gb.antonov.j611.entities.Role;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OurUserService implements UserDetailsService
{
    private final OurUsersRepo ourUsersRepo;


    public Optional<OurUser> findByLogin (String login)
    {
        return ourUsersRepo.findByLogin (login);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername (String login) throws UsernameNotFoundException
    {
        String errMsg = String.format ("Логин\r%s\rне зарегистрирован.", login);
        OurUser ourUser = findByLogin(login).orElseThrow(()->new UsernameNotFoundException (errMsg));
                                                            //^ отправляем err.401 клиенту
/*  Ниже я изменил тип
с   Collection<? extends GrantedAuthority>
на  Collection<          GrantedAuthority>, т.к. IDE не давала склеивать коллекции gaRoles и gaActions.
Можно было бы создать общую коллекцию прямо в OurUser, но я оставил этот вариант, т.к. не определил,
какой из вариантов лучше (правильнее?).
*/
        Collection<GrantedAuthority> gaRoles = mapRolesToAuthorities (ourUser.getRoles());
        Collection<GrantedAuthority> gaActions = mapActionsToAuthorities (ourUser.getActions());
        gaRoles.addAll (gaActions);

        //Заполняем и возвращаем спринговую версию юзера (у него есть ещё более подробный конструктор)
        return new User(ourUser.getLogin(),
                        ourUser.getPassword(),
                        gaRoles);
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities (Collection<Role> roles)
    {
        return roles.stream()
                    .map (role->new SimpleGrantedAuthority(role.getName()))
                    .collect (Collectors.toList());
    }

    private Collection<GrantedAuthority> mapActionsToAuthorities (Collection<Action> actions)
    {
        return actions.stream()
                      .map (action->new SimpleGrantedAuthority(action.getName()))
                      .collect (Collectors.toList());
    }
}
