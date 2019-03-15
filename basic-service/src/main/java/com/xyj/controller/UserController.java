package com.xyj.controller;

import com.xyj.builder.ResponseEntity;
import com.xyj.entity.User;
import com.xyj.service.FeignUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("basic")
@Api("基础模块")
public class UserController {
    @Autowired
    private FeignUser feignUser;
    @GetMapping("/get")
    public ResponseEntity<User> get(){
        return feignUser.getUser();
    }


}
