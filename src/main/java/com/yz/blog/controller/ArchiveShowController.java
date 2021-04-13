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
        System.out.println(123);
        model.addAttribute("a123sd",blogService.archiveBlog());
        model.addAttribute("123",blogService.selectBlogNum());
        System.out.println("hello world");
        model.addAttribute("123",blogService.archiveBlog());
        model.addAttribute("blgNum",blogService.selectBlogNum());
        return "archives";
    }
}
