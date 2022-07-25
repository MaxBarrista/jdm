package com.barrista.jdm.service;

import com.barrista.jdm.domain.Role;
import com.barrista.jdm.domain.User;
import com.barrista.jdm.repos.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService
{
    @Value("${base.url}")
    private String baseUrl;

    private final UserRepo userRepo;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, MailSender mailSender, PasswordEncoder passwordEncoder)
    {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
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
        usernameFromDb = userRepo.findByEmail(user.getEmail());
        if (usernameFromDb != null && usernameFromDb.isActive())
        {
            return 2;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        sendMessage(user);
        return 0;
    }

    private void sendMessage(User user)
    {
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
    }

    public boolean activateUserWithCode(String code)
    {
        User user = userRepo.findByActivationCode(code);
        if (user == null)
        {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);
        return true;
    }

    public List<User> findAll()
    {
        return userRepo.findAll();
    }

    public void deleteUser(Long userId)
    {
        User user = userRepo.getById(userId);
        userRepo.delete(user);
    }

    public void saveUser(User user, String username, Map<String, String> form)
    {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key: form.keySet())
        {
            if (roles.contains(key))
            {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public int updateProfile(User user, String email, String oldPassword, String newPassword, String newPasswordConfirm)
    {
        // Password processing
        if (!oldPassword.isEmpty() || !newPassword.isEmpty() || !newPasswordConfirm.isEmpty())
        {
            if (!user.getPassword().equals(oldPassword))
            {
                return 1;
            }
            else if (newPassword == null || newPassword.isEmpty())
            {
                return 2;
            }
            else if (!newPassword.equals(newPasswordConfirm))
            {
                return 3;
            }
            else
            {
                user.setPassword(newPassword);
            }
        }

        // Email processing
        String oldEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(oldEmail))
                || (oldEmail != null && !oldEmail.equals(email));

        if (isEmailChanged)
        {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email))
            {
                user.setActivationCode(UUID.randomUUID().toString());
            }
            sendMessage(user);
        }
        userRepo.save(user);
        return 0;
    }
}
