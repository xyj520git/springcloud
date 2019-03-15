package com.xyj.beans.hystrix;

import com.xyj.api.hystrix.UserHystrix;
import com.xyj.service.FeignUser;
import org.springframework.stereotype.Component;

@Component
public class FeignUserHystrix extends UserHystrix implements FeignUser{
}
