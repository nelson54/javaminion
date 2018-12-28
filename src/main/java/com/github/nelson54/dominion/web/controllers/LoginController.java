package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.User;
import com.github.nelson54.dominion.UsersProvider;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Controller
public class LoginController {

    private UsersProvider usersProvider;

    @ResponseBody
    @RequestMapping(value="/user/${id}", method=RequestMethod.GET, produces="application/json")
    User user(@PathVariable String id) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        return usersProvider.getUserById(id);
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    String login(
            @RequestParam
            String username
    ) {
        User user = new User(UUID.randomUUID().toString());
        user.setName(username);

        usersProvider.addUser(user);

        //Authentication auth = new UsernamePasswordAuthenticationToken(user.getId(), null, getAuthorities());
        //SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/";
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    String logout() {
        //SecurityContextHolder.getContext().setAuthentication(null);

        return "redirect:/";
    }

    /*private Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = () -> "ROLE_USER";
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }*/

    public void setUsersProvider(UsersProvider usersProvider) {
        this.usersProvider = usersProvider;
    }
}
