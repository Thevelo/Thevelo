package com.yz.blog.controller;


import com.yz.blog.service.blogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {
    @Autowired
    blogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){

        model.addAttribute("asd",blogService.archiveBlog());
        model.addAttribute("blogNum",blogService.selectBlogNum());
        return "archives";
    }
}
