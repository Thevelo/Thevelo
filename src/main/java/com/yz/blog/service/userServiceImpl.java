package com.yz.blog.service;

import com.yz.blog.Bean.User;

import com.yz.blog.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class userServiceImpl implements userService {

    @Resource
    com.yz.blog.dao.userDao userDao;
    @Override
    public User checkUser(String username, String password) {
        User user = userDao.find(username, MD5Utils.code(password));
        return user;
    }
}
