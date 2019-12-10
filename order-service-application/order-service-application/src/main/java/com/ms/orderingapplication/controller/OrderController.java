package com.ms.orderingapplication.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ms.orderingapplication.repository.GroceriesInventoryRepository;
import com.ms.orderingapplication.repository.OrderInformationRepository;
import com.ms.orderingapplication.model.AuthenticationToken;
import com.ms.orderingapplication.model.GroceriesInventory;
import com.ms.orderingapplication.model.OrderData;
import com.ms.orderingapplication.model.OrderInformation;
import com.ms.orderingapplication.model.OrderInputData;
import com.ms.orderingapplication.model.OrderResponseData;
import com.ms.orderingapplication.model.UserData;

@RestController
public class OrderController {
	@Autowired
	OrderInformationRepository orderInformationRepository;

	@Autowired
	GroceriesInventoryRepository groceriesInventoryRepository;

	@Autowired
	AuthenticationProxy authenticationProxy;

	
	@PostMapping("/placeOrder")
	public ResponseEntity<Object> login(@RequestBody OrderInformation orderInformation) throws Exception {

		orderInformation.setDateOfPurchase(LocalDateTime.now());
		//////
		UserData userData = new UserData();
		userData.setEmail(orderInformation.getEmail());
		userData.setFirstName(orderInformation.getFirstName());
		userData.setLastName(orderInformation.getLastName());
		userData.setApiKey(orderInformation.getApiKey());

		//////
		AuthenticationToken authenticationTokensData1 = authenticationProxy.getAuthenticationData1(userData);
		System.out.println("post call successful");

		if (authenticationTokensData1.getErrorMessage() != null
				&& !authenticationTokensData1.getErrorMessage().equals("")) {
			return new ResponseEntity<>(authenticationTokensData1.getErrorMessage(), HttpStatus.NOT_FOUND);
		}

		// validate Order
		List<OrderData> orderDataList = orderInformation.getOrderData();
		for (OrderData orderData : orderDataList) {

			GroceriesInventory groceryInventory = groceriesInventoryRepository.findInventory(orderData.getProductName(),
					orderData.getQuantity(), orderData.getBrandName(), orderData.getModelNumber());
			if (groceryInventory == null || groceryInventory.getId() <= 0) {
				return new ResponseEntity<>(
						"inventory not available for " + orderData.getProductName() + " please try your order again",
						HttpStatus.NOT_FOUND);
			}
		}
		orderInformation.setUserToken(authenticationTokensData1.getUserToken());
		orderInformation.setServiceToken(authenticationTokensData1.getServiceToken());
		orderInformation.setTransactionToken(authenticationTokensData1.getTransactionToken());
		orderInformation.setOrderStatus("PLACED");

		orderInformationRepository.save(orderInformation);
		OrderResponseData orderResponseData = new OrderResponseData();
		orderResponseData.setOrderId(orderInformation.getId());

		orderResponseData.setMessage("order placed successfully , please note the Order Id = "
				+ orderInformation.getId() + " for tracking purposes ");
		orderResponseData.setStatus("PLACED");
		orderResponseData.setServiceToken(orderInformation.getServiceToken());
		orderResponseData.setTransactionToken(orderInformation.getTransactionToken());

		return new ResponseEntity<>(orderResponseData, HttpStatus.OK);
	}

	
	@PostMapping("/submitOrder")
	public ResponseEntity<Object> submitOrder(@RequestBody OrderInputData orderInputData) throws Exception {
		AuthenticationToken authenticationTokensData = authenticationProxy
				.getAuthenticationData(orderInputData.getApiKey());
		if (authenticationTokensData == null) {
			return new ResponseEntity<>("user not authorized to retrieve the order Information", HttpStatus.OK);
		}
		OrderInformation orderInformation = orderInformationRepository.findByOrderId(orderInputData.getOrderId());

		if (!orderInputData.getTransactionToken().equals(orderInformation.getTransactionToken()))
			return new ResponseEntity<>(
					"user not authorized to retrieve the order Information as transaction token is not correct",
					HttpStatus.OK);

		if (orderInformation == null || orderInformation.getId() <= 0)
			return new ResponseEntity<>("orderId  not found , please submit correct order ", HttpStatus.OK);

		orderInformationRepository.updateConvFactor(orderInformation.getId(), "COMPLETED");
		return new ResponseEntity<>(
				"your order ( OrderId - " + orderInformation.getId() + " ) has been completed successfully",
				HttpStatus.OK);
	}

	@PostMapping("/retrieveOrder")
	public ResponseEntity<Object> retrieveOrder(@RequestBody OrderInputData orderInputData) throws Exception {
		AuthenticationToken authenticationTokensData = authenticationProxy
				.getAuthenticationData(orderInputData.getApiKey());
		if (authenticationTokensData == null) {
			return new ResponseEntity<>("user not authorized to retrieve the order Information", HttpStatus.OK);
		}
		OrderInformation orderInformation = orderInformationRepository.findByOrderId(orderInputData.getOrderId());

		if (!orderInputData.getServiceToken().equals(orderInformation.getServiceToken()))
			return new ResponseEntity<>(
					"user not authorized to retrieve the order Information as Service Token is not correct",
					HttpStatus.OK);

		return new ResponseEntity<>(orderInformation, HttpStatus.OK);
	}

	
	@PostMapping("/placeReturnRequest")
	public ResponseEntity<Object> placeReturnRequest(@RequestBody OrderInputData orderInputData) throws Exception {
		AuthenticationToken authenticationTokensData = authenticationProxy
				.getAuthenticationData(orderInputData.getApiKey());

		if (authenticationTokensData == null) {
			return new ResponseEntity<>(
					"user not authorized to place the return request as api key passed is not valid", HttpStatus.OK);
		}
		OrderInformation orderInformation = orderInformationRepository.findByOrderId(orderInputData.getOrderId());

		System.out.println("orderInputData.getServiceToken()" + orderInputData.getServiceToken());
		System.out.println("orderInformation.getServiceToken()" + orderInformation.getServiceToken());
		if (!orderInputData.getServiceToken().equals(orderInformation.getServiceToken()))
			return new ResponseEntity<>("user not authorized to place the return request as service token is not valid",
					HttpStatus.OK);

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime previousTime = now.minusDays(90);
		if (orderInformation.getDateOfPurchase().isBefore(previousTime)) {
			return new ResponseEntity<>("sorry , your order is more than 90 days old , return request cannot be posted",
					HttpStatus.OK);
		}

		return new ResponseEntity<>("return request for the order placed successfully", HttpStatus.OK);
	}

}
