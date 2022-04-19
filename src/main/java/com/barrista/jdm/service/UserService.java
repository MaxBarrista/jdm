package com.barrista.jdm.service;

import com.barrista.jdm.domain.Role;
import com.barrista.jdm.domain.User;
import com.barrista.jdm.repos.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService
{
    @Value("${base.url}")
    private String baseUrl;

    private final UserRepo userRepo;
    private final MailSender mailSender;

    public UserService(UserRepo userRepo, MailSender mailSender)
    {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepo.findByUsername(username);
    }

    public int addUser(User user) {
        User usernameFromDb = userRepo.findByUsername(user.getUsername());

        if (usernameFromDb != null)
        {
            return 1;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);

        if (!StringUtils.isEmpty(user.getEmail()))
        {
            String message = String.format(
                    "Hello, %s! \n"
                            + "Welcome to JDM! Please visit next link to verify your email: " + baseUrl + "activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return 0;
    }

    public boolean activateCode(String code)
    {
        User user = userRepo.findByActivationCode(code);
        if (user == null)
        {
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);
        return false;
    }
}
