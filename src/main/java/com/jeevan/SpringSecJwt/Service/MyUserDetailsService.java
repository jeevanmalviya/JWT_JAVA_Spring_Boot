package com.jeevan.SpringSecJwt.Service;

import com.jeevan.SpringSecJwt.Model.UserPrinciple;
import com.jeevan.SpringSecJwt.Model.Users;
import com.jeevan.SpringSecJwt.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username);
        if(user ==null){
            System.out.println("User not Found of username - "+ username);
            throw new UsernameNotFoundException("User not Found of username - "+ username);
        }
        return new UserPrinciple(user);
    }
}
