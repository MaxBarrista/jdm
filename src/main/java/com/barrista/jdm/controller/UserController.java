package com.barrista.jdm.controller;

import com.barrista.jdm.domain.Role;
import com.barrista.jdm.domain.User;
import com.barrista.jdm.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model)
    {
        model.addAttribute("users", userService.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model)
    {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("delete/{userId}")
    public String delete(@PathVariable Long userId)
    {
        userService.deleteUser(userId);

        return "redirect:/user";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    )
    {
        userService.saveUser(user, username, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String message,
            @RequestParam(required = false, defaultValue = "") String errorMessage)
    {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        if (!message.isEmpty())
        {
            model.addAttribute("message", message);
        }
        if (!errorMessage.isEmpty())
        {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String email,
            @RequestParam(required = false, defaultValue = "") String oldPassword,
            @RequestParam(required = false, defaultValue = "") String newPassword,
            @RequestParam(required = false, defaultValue = "") String newPasswordConfirm
    ) throws UnsupportedEncodingException
    {
        int statusCode = userService.updateProfile(user, email, oldPassword, newPassword, newPasswordConfirm);
        if (statusCode == 0)
        {
            String message = "Changes saved!";
            return "redirect:/user/profile?message=" + URLEncoder.encode(message, "ISO-8859-1");
        }
        else if (statusCode == 1)
        {
            String errorMessage = "Old password does not match";
            return "redirect:/user/profile?errorMessage=" + URLEncoder.encode(errorMessage, "ISO-8859-1");
        } else if (statusCode == 2)
        {
            String errorMessage = "New password can not be empty";
            return "redirect:/user/profile?errorMessage=" + URLEncoder.encode(errorMessage, "ISO-8859-1");
        } //
        else if (statusCode == 3)
        {
            String errorMessage = "New passwords don't match";
            return "redirect:/user/profile?errorMessage=" + URLEncoder.encode(errorMessage, "ISO-8859-1");
        } else
        {
            String errorMessage = "Error";
            return "redirect:/user/profile?errorMessage=" + URLEncoder.encode(errorMessage, "ISO-8859-1");
        }
    }
}
