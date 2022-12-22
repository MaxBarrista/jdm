package com.barrista.jdm.controller;

import com.barrista.jdm.domain.User;
import com.barrista.jdm.domain.dto.CaptchaResponseDto;
import com.barrista.jdm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController
{
    @Value("${recaptcha.secret}")
    private String reCaptchaSecret;

    @Value("${recaptcha.url}")
    private String reCaptchaBaseURL;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("g-recaptcha-response") String gReCaptchaResponse,
            @RequestParam String confirmPassword,
            @Valid User user,
            BindingResult bindingResult,
            Model model)
    {
        boolean success = true;

        String url = String.format(reCaptchaBaseURL + "?secret=%s&response=%s", reCaptchaSecret, gReCaptchaResponse);
        CaptchaResponseDto response = restTemplate
                .postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if (response == null)
        {
            model.addAttribute("captchaError", "Captcha error");
            success = false;
        }
        else if (!response.isSuccess())
        {
            model.addAttribute("captchaError", "Fill captcha");
            success = false;
        }
        if (bindingResult.hasErrors())
        {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            success = false;
        }
        else if (success) // If no errors found yet
        {
            if (!user.getPassword().equals(confirmPassword))
            {
                model.addAttribute("confirmPasswordError", "Passwords do not match");
                success = false;
            }
            Map<String, String> errorMap = userService.addUser(user);
            if (errorMap.size() > 0)
            {
                model.mergeAttributes(errorMap);
                return "registration";
            }
        }
        if (success)
        {
            model.addAttribute("message", "We sent an activation code to " + user.getEmail()
                    + ". Check it to verify your your email");
            return "login";
        }
        else
        {
            return "registration";
        }
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
