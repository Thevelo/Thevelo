package com.yz.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yz.blog.Bean.Type;
import com.yz.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class typeController {

    @Resource
    private TypeService TypeServiceImpl;

    // 分类的分页展示页面
    @GetMapping("/types")
    public String types(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
       // 按照排序字段，倒叙排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,5,orderBy);
        List<Type> list = TypeServiceImpl.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<Type>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }

    //回到新增分类页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }


    // 新增分类的操作
    @PostMapping("/types")
    public String postAddType(Type type, RedirectAttributes attributes){
        Type t = TypeServiceImpl.getTypeByName(type.getName());
        if(t != null){
            attributes.addFlashAttribute("message","不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        int num = TypeServiceImpl.saveType(type);
        if(num == 0 ){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    // 跳转到分类编辑页面
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable  Long id, Model model){
        model.addAttribute("type",TypeServiceImpl.getType(id));
        return "admin/types-input";
    }

    // 重新更新分类的名字
    @PostMapping("/types/{id}")
    public String postEditType(@PathVariable  Long id,Type type, RedirectAttributes attributes){
        Type t = TypeServiceImpl.getTypeByName(type.getName());
        if(t != null){
            attributes.addFlashAttribute("message","不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        int num = TypeServiceImpl.updateType(type);
        if(num == 0 ){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    // 删除分类
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable  Long id,RedirectAttributes attributes){
        TypeServiceImpl.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
