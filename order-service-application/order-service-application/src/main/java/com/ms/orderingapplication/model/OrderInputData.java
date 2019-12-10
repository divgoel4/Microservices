package com.ms.orderingapplication.model;

public class OrderInputData {

	int orderId;
	String apiKey;
	
	
	String serviceToken;
	String transactionToken;
	
	public String getTransactionToken() {
		return transactionToken;
	}
	public void setTransactionToken(String transactionToken) {
		this.transactionToken = transactionToken;
	}
	public String getServiceToken() {
		return serviceToken;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}
	
	
}
