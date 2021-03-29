package com.yz.blog.dao;

import com.yz.blog.Bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface commentRepositoryDao {


     // 单纯的根据blogid去查询，一个blog博客可能存在多条评论,多条评论根据update降序排序
     public List<Comment> getListCommentByBlogId(Long blogId);

     public int saveComment(Comment comment);

     // 得到父类评论对象的信息
     public Comment getPrentCommentById(Long prentCommentId);

     //根据创建时间倒序来排，
     List<Comment> findByBlogIdParentIdNull(@Param("blogId") Long blogId, @Param("blogParentId") Long blogParentId);

     //查询一级回复
     List<Comment> findByBlogIdParentIdNotNull(@Param("blogId") Long blogId, @Param("id") Long id);

     //查询二级回复
     List<Comment> findByBlogIdAndReplayId(@Param("blogId") Long blogId,@Param("childId") Long childId);

     //删除评论
     void deleteComment(Long id);

}
