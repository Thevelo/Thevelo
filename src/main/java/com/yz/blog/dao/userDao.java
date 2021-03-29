package com.yz.blog.dao;

import com.yz.blog.Bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
public interface userDao {
    User find(String username, String password);
}
