package com.github.nelson54.dominion.web.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class LoginController {

    @RequestMapping(value="/test", method=RequestMethod.GET)
    String hello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("username", authentication.getName());

        return "hello";
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    String login() {
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    void login(
            @RequestParam
            String username
    ) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
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
