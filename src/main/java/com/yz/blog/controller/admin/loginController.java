package com.yz.blog.controller.admin;

import com.yz.blog.Bean.User;
import com.yz.blog.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class loginController {

    @Resource
    private userService userServiceImpl;

    // 访问/admin会跳到这个方法
    @GetMapping()
    public String loginPage()
    {
        System.out.println("进了吗");
        return "admin/login";
    }

    // 登录用户
    @PostMapping("/login")
    public String login(@RequestParam  String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){

        System.out.println("进了吗1");
            User user = userServiceImpl.checkUser(username,password);

            if(user != null){
                user.setPassword(null);
                session.setAttribute("user",user);
                return "admin/index";
            }else {
                attributes.addFlashAttribute("message","用户名和密码错误");
                return "redirect:/admin";
            }
    }

    // 退出用户
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
