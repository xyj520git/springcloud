package com.xyj;

import tk.mybatis.mapper.common.MySqlMapper;

public interface Mapper<T> extends tk.mybatis.mapper.common.Mapper<T>, MySqlMapper<T> {
}
