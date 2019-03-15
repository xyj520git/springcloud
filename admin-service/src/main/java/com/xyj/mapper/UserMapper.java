package com.xyj.mapper;

import com.xyj.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {

    public List<User> getUser();
}
