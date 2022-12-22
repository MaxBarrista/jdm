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
        model.addAttribute("isActive", user.isActive());
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
            @RequestParam(required = false, defaultValue = "") String email,
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
        else
        {
            String errorMessage;
            switch (statusCode)
            {
                case 1:
                    errorMessage = "Old password does not match";
                    break;
                case 2:
                    errorMessage = "New password can not be empty";
                    break;
                case 3:
                    errorMessage = "New passwords don't match";
                    break;
                case 4:
                    errorMessage = "Email does not exist";
                    break;
                default:
                    errorMessage = "Error";
            }
            return "redirect:/user/profile?errorMessage=" + URLEncoder.encode(errorMessage, "ISO-8859-1");
        }
    }

    @GetMapping("/subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user)
    {
        if (currentUser.equals(user))
        {
            return "error";
        }
        userService.subscribe(currentUser, user);

        return "redirect:/user-cars/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user)
    {
        if (currentUser.equals(user))
        {
            return "error";
        }
        userService.unsubscribe(currentUser, user);

        return "redirect:/user-cars/" + user.getId();
    }

    @GetMapping("/{type}/{user}/list")
    public String userList(
            @PathVariable User user,
            @PathVariable String type,
            Model model)
    {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        if ("subscriptions".equals(type))
        {
            model.addAttribute("users", user.getSubscriptions());
        }
        else if ("subscribers".equals(type))
        {
            model.addAttribute("users", user.getSubscribers());
        }
        return "subscriptions";
    }
}
