package com.github.nelson54.dominion.web.users;

import com.github.nelson54.dominion.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Created by Derek on 4/16/2015.
 */
public final class SimpleSignInAdapter implements SignInAdapter {

    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        SecurityContext.setCurrentUser(new User(userId));
        return null;
    }

}
