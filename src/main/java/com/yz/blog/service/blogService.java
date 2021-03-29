package com.yz.blog.service;

import com.yz.blog.Bean.Blog;
import com.yz.blog.query.archiveShowBlog;
import com.yz.blog.query.queryBlog;
import com.yz.blog.query.showBlog;

import java.util.List;
import java.util.Map;

public interface blogService {

    // 通过id查询showblog
    showBlog getBlogByid(Long id);

    // 通过id查询blog
    Blog getBlogById(Long id);

    // 分页查询所有blog,返回一个showblog
    List<showBlog> getListBlog();

    // 查询所有blog,返回一个blog
    List<Blog> getListAllBlog();

    // 根据typeid查询所有blog,返回一个List《blog》
    List<Blog> getListAllBlog(Blog blog);

    // 根据updatetime查询是推荐的blog
    List<Blog> getRecommendedBlogs();

    // 搜索查询showblog只展示在后台
    List<showBlog> getListBlogByTitleAndTypeIdorRecommend(showBlog showBlog);

    // 搜索查询blog，根据搜索的title或者部分博客内容。
    List<Blog> getSearchBlog(String query);

    // 新增blog
    int saveBlog(Blog blog);

    // 通过id查询blog
    Blog findBlogById(Long id);

    // 修改blog
    int updateBlog(Blog blog);

    // 删除blog
    void deleteBlog(Long id);
    //
    Blog getByNameBlog(String name);

    // 查询归档信息
    // String是年份，List<Blog>是该年份下的多个blog
    Map<String,List<archiveShowBlog>> archiveBlog();

    // 查询博客条数
    int selectBlogNum();
    // 查询3条或者N条blog。显示在footer底部
    List<Blog> getRecommendedBlogsSize3();

}
