package com.xyj.mapper;

import com.xyj.annoation.DataPermAspect;
import com.xyj.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    @DataPermAspect(tablePerix="",showSql = true)
    public List<User> getUser();
}
