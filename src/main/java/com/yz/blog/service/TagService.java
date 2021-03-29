package com.yz.blog.service;

import com.yz.blog.Bean.Tag;
import com.yz.blog.Bean.Type;

import java.util.List;

public interface TagService {
    // 新增
    int saveTag(Tag tag);
    // 查询
    Tag getTag(Long id);

    List<Tag> getAllTag();
    List<Tag> listTagS(String ids);
    int updateTag(Tag tag);

    void deleteTag(Long id);

    Tag getTagByName(String name);
}
