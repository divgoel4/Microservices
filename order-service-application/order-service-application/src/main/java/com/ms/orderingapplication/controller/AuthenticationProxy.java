package com.ms.orderingapplication.controller;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ms.orderingapplication.model.UserData;
import com.ms.orderingapplication.model.AuthenticationToken;

@RibbonClient(name="loginapplication")
@FeignClient(name="loginapplication", url="http://localhost:2030")

public interface AuthenticationProxy {
	
	@RequestMapping("getAuthenticationTokensData/{apiKey}")
	public AuthenticationToken getAuthenticationData(@PathVariable(value="apiKey") String apiKey) ;
	
	@RequestMapping(value="login", method = RequestMethod.POST)
	public AuthenticationToken getAuthenticationData1(@RequestBody UserData userData) ;
	

}
