package com.xyj.service;

import com.xyj.entity.User;
import com.xyj.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public List<User> getUser() {
        return userMapper.getUser();
    }
}
