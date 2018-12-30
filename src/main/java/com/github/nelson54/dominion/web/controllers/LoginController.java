package com.github.nelson54.dominion.web.controllers;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Controller
public class LoginController {
    @RequestMapping(value="/login", method=RequestMethod.POST)
    String login(
            @RequestParam
            String username
    ) {
        Authentication auth = new UsernamePasswordAuthenticationToken(UUID.randomUUID(), null, getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/";
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);

        return "redirect:/";
    }

    private Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = () -> "ROLE_USER";
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

}
