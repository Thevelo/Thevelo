package com.yz.blog.service;

import com.yz.blog.Bean.Blog;
import com.yz.blog.NotFoundException;
import com.yz.blog.dao.blogRepositoryDao;
import com.yz.blog.query.archiveShowBlog;
import com.yz.blog.query.queryBlog;
import com.yz.blog.query.showBlog;
import com.yz.blog.util.markDownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class blogServiceImpl implements blogService {
   @Autowired
   private com.yz.blog.dao.blogRepositoryDao blogRepositoryDao;
    //通过id查询showBlog
    @Override
    public showBlog getBlogByid(Long id) {
        return blogRepositoryDao.getBlogByid(id);
    }

    // 通过id查询Blog,包括用户和type信息，博客的markdown文本内容会被转换为html文本的形式
    @Transactional
    @Override
    public Blog getBlogById(Long id){
        Blog blog = blogRepositoryDao.getBlogById(id);
        if(blog == null){
            throw  new NotFoundException("博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(markDownUtils.markdownToHtmlExtensions(content));
        blogRepositoryDao.updateViews(id);
        return b;

    }

    @Transactional
    @Override
    public List<showBlog> getListBlog() {
        return blogRepositoryDao.getAllBlogQuery();
    }

    @Override
    public List<showBlog> getListBlogByTitleAndTypeIdorRecommend(showBlog showBlog) {

        return blogRepositoryDao.getListBlogByTitleAndTypeIdorRecommend(showBlog);
    }

    // 搜索查询blog，根据搜索的title或者部分博客内容。
    @Override
    public List<Blog> getSearchBlog(String query) {
        return blogRepositoryDao.getSearchBlog(query);
    }

    // 查询推荐的博客,内部写死了推荐5个博客
    public List<Blog> getRecommendedBlogs(){

        return blogRepositoryDao.getRecommendedBlogs();
    }

    @Override
    public List<Blog> getListAllBlog(){

        return  blogRepositoryDao.getListBlog();
    }
    // 只给types页面作展示用的方法，根据Blog里的typeId属性查询
    @Override
    public List<Blog> getListAllBlog(Blog blog){

        return  blogRepositoryDao.getListBlogByTypeId(blog);
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {


        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);

        return blogRepositoryDao.saveBlog(blog);

    }

    @Override
    public Blog findBlogById(Long id) {
        return blogRepositoryDao.findBlogByid(id);
    }

    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        return blogRepositoryDao.updateBlog(blog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepositoryDao.deleteBlog(id);
    }

    @Override
    public Blog getByNameBlog(String name) {
        return null;
    }

    @Override
    @Transactional
    public Map<String,List<archiveShowBlog>> archiveBlog(){
        List<String> years = blogRepositoryDao.findGroupYears();
        Map<String,List<archiveShowBlog>>  maps = new LinkedHashMap<>();
        for(String s:years){
            maps.put(s,blogRepositoryDao.findBlogByYear(s));
        }
        return maps;
    }
    @Override
    //查询博客数量
    public int selectBlogNum(){
        return blogRepositoryDao.selectBlogNum();
    }

    @Override
    // 查询3条或者N条blog。显示在footer底部
    public List<Blog> getRecommendedBlogsSize3(){
        return blogRepositoryDao.getRecommendedBlogsSize3();
    }
}
