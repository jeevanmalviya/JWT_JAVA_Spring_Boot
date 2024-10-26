package com.jeevan.SpringSecJwt.Controller;

import com.jeevan.SpringSecJwt.Model.Users;
import com.jeevan.SpringSecJwt.Repository.UserRepo;
import com.jeevan.SpringSecJwt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public Users registraterUser(@RequestBody Users user){
          return userService.saveUser(user);
    }


    @PostMapping("/loginuser")
    public String userLogin(@RequestBody Users user){
//        System.out.println(user.getId() + " "+user.getUsername()+ " "+user.getPassword());
//       return "good Login";
        return userService.verify(user);
    }
}
