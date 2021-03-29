package com.yz.blog.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yz.blog.Bean.Blog;
import com.yz.blog.Bean.Type;
import com.yz.blog.NotFoundException;
import com.yz.blog.query.showBlog;
import com.yz.blog.service.TypeService;
import com.yz.blog.service.blogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class indexController {
    @Autowired
    private com.yz.blog.service.blogService blogService;
    @Autowired
    private TypeService typeService;

    //首页展示
    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        PageHelper.startPage(pageNum,10);
        List<Blog> allBlog = blogService.getListAllBlog();
        List<Blog> recommendedBlogs = blogService.getRecommendedBlogs();
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(allBlog);
        Long size = 6L;
        List<Type> types  = typeService.getSizeTypes(size);

        model.addAttribute("types",types);
        model.addAttribute("blogs",allBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("recommendedBlogs", recommendedBlogs);
        return "index";
    }

   /* // 博客详情页
    @GetMapping("/blog/{id}")
    public String blog(){

        System.out.println("------index------");
        return "blog";
    }*/

    // 导航栏的搜索按钮
    @PostMapping("/search")
    public String search(@RequestParam String query, Model model,
                         @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        List<Blog> searchBlog = blogService.getSearchBlog(query);
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(searchBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }
    
    // 查看博客详情
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getBlogById(id));
        return "blog";
    }


    // 文档的页脚的推荐博客

    @GetMapping("/footer/newblog")
    public String footerBlog(Model model){
        List<Blog> recommendedBlogs = blogService.getRecommendedBlogsSize3();
        model.addAttribute("recommendedBlogs", recommendedBlogs);
        return "_fragments :: newblogList" ;
    }
}
