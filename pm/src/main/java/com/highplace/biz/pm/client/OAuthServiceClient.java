package com.highplace.biz.pm.client;

import com.highplace.biz.pm.domain.system.Account;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "oauth-service")
public interface OAuthServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/uaa/user/{name}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Account getUserAccount(@PathVariable("name") String name);

    @RequestMapping(method = RequestMethod.POST, value = "/uaa/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void createUserAccount(Account account);

    @RequestMapping(method = RequestMethod.PUT, value = "/uaa/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void modifyUserAccount(Account account);
}

