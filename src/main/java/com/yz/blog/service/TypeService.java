package com.yz.blog.service;


import com.yz.blog.Bean.Type;

import java.util.List;


public interface TypeService {
    // 新增
    int saveType(Type type);
    // 查询
    Type getType(Long id);

    List<Type> getAllType();

    int updateType(Type type);

    void deleteType(Long id);

    List<Type> getSizeTypes(Long size);

    Type getTypeByName(String name);
}
