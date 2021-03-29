package com.yz.blog.controller;

import com.yz.blog.Bean.Comment;
import com.yz.blog.Bean.User;
import com.yz.blog.service.blogService;
import com.yz.blog.service.commentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class commentController {

    @Value("${comment.avatar}")
    private String avatar;

    @Autowired
    private com.yz.blog.service.commentService commentService;
    @Autowired
    private com.yz.blog.service.blogService blogService;

    // 留言区
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        List<Comment> comments = commentService.getListCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    // 新增评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){

        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlogById(blogId));
        User user = (User)session.getAttribute("user");
        // 传入当前登录用户的头像。因为只有登录后才能评论.

        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            /* 根据user的username显示评论账号
            comment.setUsername(user.getUsername());*/
        }else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;

    }
}
