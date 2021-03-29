package com.yz.blog.dao;

import com.yz.blog.Bean.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface typeRepositoryDao {

    int saveType(Type type);

    Type getType(Long id);

    List<Type> getAllType();

    int updateType(Type type);

    void deleteType(Long id);

    Type getTypeByName(String name);
    // 查询条数写死，根据博客数量降序显示
    List<Type> getSizeTypes(@Param("size") Long size);

}
