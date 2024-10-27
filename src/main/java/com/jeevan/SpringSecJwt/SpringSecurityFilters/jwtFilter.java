package com.jeevan.SpringSecJwt.SpringSecurityFilters;

import com.jeevan.SpringSecJwt.Service.JWTService;
import com.jeevan.SpringSecJwt.Service.MyUserDetailsService;
import com.jeevan.SpringSecJwt.config.SecurityConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class jwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // on the server side it will recive is
        //1 Bearar eyJzdWIiOiJheWFuIiwiaWF0IjoxNzI5OTQxMTYxLCJleHAiOjE3Mjk5NDIyNDF9 (token)

        String authHeader= request.getHeader("Authorization");
        String token =null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer")){
            token  = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        // aslo we have to check that object is not already authenticted
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){

            // it will give the userdetails object full object with the help of Applcation Contect
            UserDetails userDetails =applicationContext.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token, username)) {
                // below token ask you for three things
                //1. principle
                //2. credentials
                //3. Authorities
                UsernamePasswordAuthenticationToken usernPassAuthToken =
                        new UsernamePasswordAuthenticationToken
                                (userDetails, null, userDetails.getAuthorities());

                //this token should know about the request object
                usernPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // by this line we are adding the token in the filter chain
                SecurityContextHolder.getContext().setAuthentication(usernPassAuthToken);
            }
        }
        // once this above filter is done do the next filter chain working
        filterChain.doFilter(request, response);
    }
}
