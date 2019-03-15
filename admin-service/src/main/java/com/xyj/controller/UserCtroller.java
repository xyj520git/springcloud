package com.xyj.controller;

import com.xyj.builder.ResponseEntity;
import com.xyj.entity.User;
import com.xyj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserCtroller {
    @Autowired
    private UserService userService;

    @ApiOperation("获取用户")
    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(){
      List<User> list= userService.getUser();
      return  new ResponseEntity(list);
    }

}
