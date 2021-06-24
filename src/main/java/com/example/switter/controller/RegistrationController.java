package com.example.switter.controller;

import com.example.switter.domain.User;
import com.example.switter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {

        // если не смогли добавить пользователя, значит данный пользователь существует
        if(!userService.addUser(user)) {
            model.put("message", "User exists");
            return "registration";
        }

        return "redirect:/login"; // при успешной регистрации редирект на стр логина
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) { // в model -- ледит сообщение, в code - код активации
        // логика активации
        boolean isActivated = userService.activateUser(code);

        // итог активации кода
        if (isActivated) {
            model.addAttribute("message", "Profile successfully activated");
        } else {
            model.addAttribute("message", "Activation code not found!");
        }
        return "login";
    }
}