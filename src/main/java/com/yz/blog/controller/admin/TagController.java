package com.yz.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yz.blog.Bean.Tag;
import com.yz.blog.Bean.Type;
import com.yz.blog.service.TagService;
import com.yz.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Resource
    private TagService TagServiceImpl;

    // 分类的分页展示页面
    @GetMapping("/tags")
    public String tags(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
       // 按照排序字段，倒叙排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,5,orderBy);

        List<Tag> list = TagServiceImpl.getAllTag();
        PageInfo<Tag> pageInfo = new PageInfo<Tag>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/tags";
    }

    //回到新增分类页面
    @GetMapping("/tags/input")
    public String inputTag(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }


    // 新增分类的操作
    @PostMapping("/tags")
    public String postAddTag(Tag tag, RedirectAttributes attributes){
        Tag t = TagServiceImpl.getTagByName(tag.getName());
        if(t != null){
            attributes.addFlashAttribute("message","不能添加重复的分类");
            return "redirect:/admin/tags/input";
        }
        int num = TagServiceImpl.saveTag(tag);
        if(num == 0 ){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    // 跳转到分类编辑页面
    @GetMapping("/tags/{id}/input")
    public String editInputTag(@PathVariable  Long id, Model model){
        model.addAttribute("tag",TagServiceImpl.getTag(id));
        return "admin/tags-input";
    }

    // 重新更新分类的名字
    @PostMapping("/tags/{id}")
    public String postEditTag(@PathVariable  Long id,Tag tag, RedirectAttributes attributes){
        Tag t = TagServiceImpl.getTagByName(tag.getName());
        if(t != null){
            attributes.addFlashAttribute("message","不能添加重复的分类");
            return "redirect:/admin/tags/input";
        }
        int num = TagServiceImpl.updateTag(tag);
        if(num == 0 ){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    // 删除分类
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable  Long id,RedirectAttributes attributes){
        TagServiceImpl.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
