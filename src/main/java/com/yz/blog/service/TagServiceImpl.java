package com.yz.blog.service;

import com.yz.blog.Bean.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private com.yz.blog.dao.tagRepositoryDao tagRepositoryDao;
    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagRepositoryDao.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {

        return tagRepositoryDao.getTag(id);
    }
    @Transactional
    @Override
    public List<Tag> getAllTag() {
        return tagRepositoryDao.getAllTag();
    }

    @Override
    public List<Tag> listTagS(String ids) {
        List<Long> longs = converToList(ids);
        return tagRepositoryDao.findAllTag(longs);
    }
    // 转换方法
    private List<Long> converToList(String ids){
        List<Long> list =new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idArray = ids.split(",");
            for(int i = 0;i<idArray.length;i++){
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagRepositoryDao.updateTag(tag);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepositoryDao.deleteTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepositoryDao.getTagByName(name);
    }


}
