package com.jeevan.SpringSecJwt.Controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String sayHello(HttpServletRequest httpServletResposne){
        return "Hello Darling Aditya"+ " Session Id - "+httpServletResposne.getSession().getId();
    }
}
