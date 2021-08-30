package ru.gb.antonov.j611.beans.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.antonov.j611.beans.services.OurUserService;
import ru.gb.antonov.j611.entities.OurUser;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthController
{
    private final OurUserService ourUserService;

    //http://localhost:8189/qwerty
    @GetMapping("/")
    public String homePage() {   return "welcome (возможен доступ без авторизации)";   }

    //http://localhost:8189/qwerty/user_info
    @GetMapping("/user_info")
    public String daoTestPage (Principal principal)
    {
        String errMsg = "Unable to find user by login: " + principal.getName();

        OurUser ourUser = ourUserService.findByLogin (principal.getName())
                                        .orElseThrow(()->new RuntimeException(errMsg));

        return "Authenticated user info: "+ ourUser.getLogin() +" : "+ ourUser.getEmail();
    }

    //http://localhost:8189/qwerty/cafe
    @GetMapping("/cafe")
    public String cafePage() {   return "cafe (возможен доступ без авторизации)";   }

    //http://localhost:8189/qwerty/exhibition
    @GetMapping("/exhibition")
    public String exhibitionPage() {   return "exhibition (для всех авторизованных пользователей)";   }

    //http://localhost:8189/qwerty/garden
    @GetMapping("/garden")
    public String gardenPage() {   return "garden (для авторизованных пользователей с правм WALK)";   }

    //http://localhost:8189/qwerty/chat
    @GetMapping("/chat")
    public String chatPage() {   return "chat (для авторизованных пользователей с правм TALK)";   }

    //http://localhost:8189/qwerty/office
    @GetMapping("/office")
    public String officePage() {   return "office (только для персонала: ADMIN, SUPERADMIN)";   }

    //http://localhost:8189/qwerty/office/diningroom
    @GetMapping("/office/diningroom")
    public String diningroomPage() {   return "diningroom (для авторизованных пользователей с правм EAT)";   }

    //http://localhost:8189/qwerty/office/controltroom
    @GetMapping("/office/controlroom")
    public String restroomPage() {   return "controlroom (для авторизованных пользователей с правм SLEEP)";   }

}
