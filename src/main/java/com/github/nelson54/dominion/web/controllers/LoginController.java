package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.User;
import com.github.nelson54.dominion.UsersProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Controller
public class LoginController {

    @Inject
    UsersProvider usersProvider;

    @RequestMapping(value="/test", method=RequestMethod.GET)
    String hello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = usersProvider.getUserById(authentication.getName());
        model.addAttribute("username", user.getName());

        return "hello";
    }

    @ResponseBody
    @RequestMapping(value="/user", method=RequestMethod.GET, produces="application/json")
    User user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return usersProvider.getUserById(authentication.getName());
    }



    @RequestMapping(value="/login", method=RequestMethod.GET)
    String login() {
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    String login(
            @RequestParam
            String username
    ) {
        User user = new User(UUID.randomUUID().toString());
        user.setName(username);

        usersProvider.addUser(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getId(), null, getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/";
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "login";
    }

    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = () -> "ROLE_USER";
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

}
