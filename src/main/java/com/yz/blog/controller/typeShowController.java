package com.yz.blog.controller;

import com.github.pagehelper.PageInfo;
import com.yz.blog.Bean.Blog;
import com.yz.blog.Bean.Type;
import com.yz.blog.service.TypeService;
import com.yz.blog.service.blogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class typeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private com.yz.blog.service.blogService blogService;

    // 展示页
    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,
                        @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        Model model){
        Long size = 1000L;
        List<Type> types = typeService.getSizeTypes(size);
        if(id == -1 ){
            id = types.get(0).getId();
        }

        Blog blog = new Blog();
        blog.setTypeId(id);
        List<Blog> blogs = blogService.getListAllBlog(blog);
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogs);

        model.addAttribute("types",types);

        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
