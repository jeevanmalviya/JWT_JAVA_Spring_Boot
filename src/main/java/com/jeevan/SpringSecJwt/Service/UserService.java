package com.jeevan.SpringSecJwt.Service;

import com.jeevan.SpringSecJwt.Model.Users;
import com.jeevan.SpringSecJwt.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder bcryptPass = new BCryptPasswordEncoder();

    public Users saveUser(@RequestBody Users user){
        String bcryptPassword = bcryptPass.encode(user.getPassword());
        user.setPassword(bcryptPassword);
       return userRepo.save(user);
    }

    public String verify(@RequestBody Users user) {
        Authentication authentication =
                authManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername() , user.getPassword()));
        if(authentication.isAuthenticated())
            return "Login SuccessFull its Verified by Spring boot security";

        return "not Verified";
    }
}
