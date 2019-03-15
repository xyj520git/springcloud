package com.xyj.service;

import com.xyj.api.UserApi;
import com.xyj.beans.hystrix.FeignUserHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "admin-service",fallback = FeignUserHystrix.class)
public interface FeignUser extends UserApi{
}
