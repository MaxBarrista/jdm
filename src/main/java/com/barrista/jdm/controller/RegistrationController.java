package com.barrista.jdm.controller;

import com.barrista.jdm.domain.User;
import com.barrista.jdm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            User user,
            Map<String, Object> model,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword)
    {
        if (!password.equals(confirmPassword))
        {
            model.put("errorMessage", "Passwords do not match");
            return "registration";
        }
        else
        {
            int errorMessage = userService.addUser(user);
            if (errorMessage == 1)
            {
                model.put("errorMessage", "This username is already taken");
                return "registration";
            }
            else if (errorMessage == 2)
            {
                model.put("errorMessage", "This email is already taken");
                return "registration";
            }
        }
        model.put("message", "We sent an activation code to " + user.getEmail()
                + ". Check it to verify your your email");
        return "login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code)
    {
        boolean isActivated = userService.activateUserWithCode(code);

        if (isActivated)
        {
            model.addAttribute("message", "User successfully activated!");
        }
        else
        {
            model.addAttribute("errorMessage", "User is already activated");
        }
        return "login";
    }
}
