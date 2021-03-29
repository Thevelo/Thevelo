package com.yz.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class abouController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
