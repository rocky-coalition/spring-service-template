package com.coalition.sample.integration.api;

import com.coalition.sample.integration.api.model.UserModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="auth-user", url ="${auth-management.url}")
public interface SampleClient {
    @RequestMapping(method= RequestMethod.GET, value="/v1/users/{email}")
    UserModel getUser(@PathVariable("email") String email, @RequestHeader("Authorization") String bearerToken);
}
