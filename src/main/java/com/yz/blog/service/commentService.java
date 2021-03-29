package com.yz.blog.service;

import com.yz.blog.Bean.Comment;

import java.util.List;

public interface commentService {

    // 获取评论列表,通过blog的id获取
    List<Comment> getListCommentByBlogId(Long blogId);

    // 保存评论
    int saveComment(Comment comment);

    //删除评论
    void deleteComment(Comment comment,Long id);


}
