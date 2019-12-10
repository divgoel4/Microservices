package com.ms.orderingapplication.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@Entity
@Table(name="order_information")
public class OrderInformation {
		int id;
		String apiKey;
		String userToken;
		String firstName;
		String lastName;
		String email;
		LocalDateTime dateOfPurchase;
		String transactionToken;
		String serviceToken;
		String orderStatus;
		
		
		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		List<OrderData> orderData;
	public OrderInformation() {
		
	}
	
	public OrderInformation(int id, String userToken , String apiKey, String productName,int quantity, LocalDateTime dateOfPurchase,String transactionToken,String serviceToken , List<OrderData> orderList) {
		super();
		this.id = id;
		this.userToken = userToken;
		this.apiKey = apiKey;
		this.dateOfPurchase = dateOfPurchase;
		this.transactionToken = transactionToken;
		this.serviceToken = serviceToken;
		this.orderData = orderList;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="user_token",columnDefinition="LONGTEXT")
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	
	@Column(name="date_of_purchase")
	public LocalDateTime getDateOfPurchase() {
		return dateOfPurchase;
	}
	public void setDateOfPurchase(LocalDateTime dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	
	
	@Column(name="transaction_token",columnDefinition="LONGTEXT")
	public String getTransactionToken() {
		return transactionToken;
	}
	public void setTransactionToken(String transactionToken) {
		this.transactionToken = transactionToken;
	}
	@Column(name="service_token",columnDefinition="LONGTEXT")
	public String getServiceToken() {
		return serviceToken;
	}
	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="orderId")
	@ElementCollection(targetClass=OrderData.class)
	public List<OrderData> getOrderData() {
		return orderData;
	}
	public void setOrderData(List<OrderData> orderData) {
		this.orderData = orderData;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	
	
	
}
