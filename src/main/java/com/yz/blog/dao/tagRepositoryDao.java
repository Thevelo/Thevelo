package com.yz.blog.dao;

import com.yz.blog.Bean.Tag;
import com.yz.blog.Bean.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface tagRepositoryDao {
    int saveTag(Tag tag);

    Tag getTag(Long id);

    List<Tag> getAllTag();

    List<Tag> findAllTag(List<Long> ids);

    int updateTag(Tag tag);

    void deleteTag(Long id);

    Tag getTagByName(String name);
}
