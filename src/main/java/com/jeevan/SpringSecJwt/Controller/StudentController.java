package com.jeevan.SpringSecJwt.Controller;

import com.jeevan.SpringSecJwt.Model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(List.of(
            new Student(1, "Navin" , 90),
            new Student(2,"Jeevan" , 59)
    ));

    @GetMapping("/getstudent")
    private List<Student> getStudent(){
        return students;
    }

    // two ways to get the CSRF Token from the servlet
    @GetMapping("csrf")
    public CsrfToken getCsrfToken(HttpServletRequest req){
        return (CsrfToken)req.getAttribute("_csrf");
    }

    @GetMapping("csrf-token")
    public String getCSRFToken(CsrfToken req){
        String token = req.getToken();
        return token;
    }

    @PostMapping("addstudent")
    public Student addStudent(@RequestBody Student student){
        students.add(student);
        return student;
    }

}
