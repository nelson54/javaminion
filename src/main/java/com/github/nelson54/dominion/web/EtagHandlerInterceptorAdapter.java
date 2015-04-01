package com.github.nelson54.dominion.web;

import com.github.nelson54.dominion.GameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


@Component
public class EtagHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    @Autowired
    GameProvider gameProvider;

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            ServletException, IOException {
        String method = request.getMethod();
        if (!"GET".equals(method))
            return true;

        String previousToken = request.getHeader("If-None-Match");
        String[] path = request.getServletPath().split("/");

        if(path.length >= 3 && path[2].equals("recommended") || path.length < 3){
            return true;
        }

        String gameId = path[2];

        //String token = Integer.toString(gameProvider.getGameByUuid(gameId).hashCode());
        Random random = new Random();
        String token = Integer.toString(random.nextInt());
        // compare previous token with current one
        if ((token != null) && (previousToken != null && previousToken.equals('"' + token + '"'))) {
            response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
            // re-use original last modified timestamp
            response.setHeader("Last-Modified", request.getHeader("If-Modified-Since"));
            return false; // no further processing required
        }

        // set header for the next time the client calls
        if (token != null) {
            response.setHeader("ETag", '"' + token + '"');

            // first time through - set last modified time to now
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MILLISECOND, 0);
            Date lastModified = cal.getTime();
            response.setDateHeader("Last-Modified", lastModified.getTime());
        }

        return true;
    }
}
