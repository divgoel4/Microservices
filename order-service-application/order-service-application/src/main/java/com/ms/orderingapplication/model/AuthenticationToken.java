package com.ms.orderingapplication.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class AuthenticationToken {
	int id;
	String apiKey;
	String userToken;
	String serviceToken;
	String transactionToken;
	String userName;
	int expiresIn;
	LocalDateTime createDate;
	String errorMessage;
	
	
	
	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public AuthenticationToken() {
		
	}
	
	public AuthenticationToken(String userToken, String serviceToken, String transactionToken,String userName , int expiresIn) {
		super();
		this.userToken = userToken;
		this.serviceToken = serviceToken;
		this.transactionToken = transactionToken;
		this.userName = userName;
		this.expiresIn = expiresIn;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	public String getServiceToken() {
		return serviceToken;
	}
	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}
	
	public String getTransactionToken() {
		return transactionToken;
	}
	public void setTransactionToken(String transactionToken) {
		this.transactionToken = transactionToken;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public int getExpiresIn() {
		return expiresIn;
	}


	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	

}
