package com.example.testauth2;



import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class testapi {
    @RequestMapping("/test")
    public String test( @RequestAttribute("Email") String email, @RequestAttribute("Id") String id, @RequestAttribute("Name") String name) {


        return email + " " + id + " " + name;
    }
}