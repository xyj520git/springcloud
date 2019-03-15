package com.xyj.api;

import com.xyj.builder.ResponseEntity;
import com.xyj.entity.User;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserApi {
    /**
     * 获取用户
     * @return
     */
    @GetMapping("/user/getUser")
    ResponseEntity<User> getUser();
}
