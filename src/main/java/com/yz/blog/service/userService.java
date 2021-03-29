package com.yz.blog.service;

import com.yz.blog.Bean.User;
import org.springframework.stereotype.Service;

@Service
public interface userService {

    User checkUser(String username, String password);
}
