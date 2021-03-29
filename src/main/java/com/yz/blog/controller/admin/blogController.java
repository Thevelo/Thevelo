package com.yz.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yz.blog.Bean.Blog;
import com.yz.blog.Bean.Type;
import com.yz.blog.Bean.User;
import com.yz.blog.query.queryBlog;
import com.yz.blog.query.showBlog;
import com.yz.blog.service.TagService;
import com.yz.blog.service.TypeService;
import com.yz.blog.service.blogService;
import com.yz.blog.service.blogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class blogController {
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private com.yz.blog.service.blogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    // 进入blog表分页的页面
    @GetMapping("/blogs")
    public String blogs(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        //按照排序字段 倒序 排序
        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum,5,orderBy);
        List<showBlog> list = blogService.getListBlog();
        PageInfo<showBlog> pageInfo = new PageInfo<showBlog>(list);
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs";
    }

    // 搜索查询，通过title，推荐，type查询。只局部刷新在table中
    @PostMapping("/blogs/search")
    public String SerachBlogs(showBlog showBlog,Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        //按照排序字段 倒序 排序
        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum,5,orderBy);

        List<showBlog> list = blogService.getListBlogByTitleAndTypeIdorRecommend(showBlog);
        PageInfo<showBlog> pageInfo = new PageInfo<showBlog>(list);
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs :: blogList";

        }


    // 跳转新增页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("tags",tagService.getAllTag());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    // 新增blog
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setUser((User) session.getAttribute("user"));
        //设置blog的type
        blog.setType(typeService.getType(blog.getTypeId()));
        if (!"".equals(blog.getTagIds())){
            blog.setTags(tagService.listTagS(blog.getTagIds()));
        }
        int b = blogService.saveBlog(blog);
        if(b == 0){
            attributes.addFlashAttribute("message", "新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }

        return REDIRECT_LIST;
    }

    // 跳转到编辑修改页面
    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable  Long id, Model model){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("tags",tagService.getAllTag());
        Blog blog = blogService.findBlogById(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    //    编辑修改文章
    @PostMapping("/blogs/{id}")
    public String editPost(Blog Blog, RedirectAttributes attributes) {
        int b = blogService.updateBlog(Blog);
        if(b ==0){
            attributes.addFlashAttribute("message", "修改失败");
        }else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/blogs";
    }

    // 删除blog
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }
}
