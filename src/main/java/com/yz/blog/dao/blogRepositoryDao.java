package com.yz.blog.dao;

import com.yz.blog.Bean.Blog;
import com.yz.blog.query.archiveShowBlog;
import com.yz.blog.query.queryBlog;
import com.yz.blog.query.showBlog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface blogRepositoryDao {
    // 通过id查询showblog
    showBlog getBlogByid(Long id);

    // 通过id查询Blog,包括用户和type信息
    Blog getBlogById(Long id);

    // 通过id查询Blog
    Blog findBlogByid(Long id);

    // 分页查询blog
    List<Blog> getListBlog();
    // 分页查询blog，根据Blog里的typeId属性查询
    List<Blog> getListBlogByTypeId(Blog blog);

    // 分页查询blog,返回的是showblog
    List<showBlog> getAllBlogQuery();

    // 查询推荐博客，内部写死了推荐的条数,如5条
    List<Blog> getRecommendedBlogs();

    // 根据条件搜索查询blog, 条件为：title，是否推荐，type
    List<showBlog> getListBlogByTitleAndTypeIdorRecommend(showBlog showBlog);

    //导航栏的搜索按钮 ，根据title和部分内容查询
    List<Blog> getSearchBlog(String query);

    // 新增blog
    int saveBlog(Blog blog);

    // 修改blog
    int updateBlog(Blog blog);

    // 删除blog
    void deleteBlog(Long id);

    // 更新浏览次数
    int updateViews(Long id);

    // 查询年份,并且封装在list中
    List<String> findGroupYears();

    // 查询年份,并且封装在list中
    List<archiveShowBlog> findBlogByYear(String year);

    // 查询博客数量
    int selectBlogNum();

    // 查询3条或者N条blog。显示在footer底部
    List<Blog> getRecommendedBlogsSize3();
}
