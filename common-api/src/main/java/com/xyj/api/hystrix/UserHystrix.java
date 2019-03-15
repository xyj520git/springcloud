package com.xyj.api.hystrix;

import com.xyj.api.UserApi;
import com.xyj.builder.ResponseEntity;
import com.xyj.entity.User;
import com.xyj.exception.SystemMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserHystrix implements UserApi{
    @Value("admin-service")
    private String name;
    @Override
    public ResponseEntity<User> getUser() {
        return new ResponseEntity<>(SystemMessage.CALL_MODUL_FAIL.result(name).getMessage());
    }
}
