package com.ibm.microservice.activity1.currencyexchangeservice;

import java.math.BigDecimal;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private ExchangeValueRepository exchangeValueRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from ,@PathVariable String to  ) {
		 ExchangeValue exchangeValue= exchangeValueRepository.findByFromAndTo(from,to);
		 logger.info("{}", exchangeValue);
		 return exchangeValue;
		//return new ExchangeValue(1000L,from,to,BigDecimal.valueOf(65));
	}
	
    @PostMapping("/currency-exchange")
	public ResponseEntity<Object> saveRetrieveExchangeValue(@RequestBody ExchangeValue exchangeValue ) {
    	
    	ExchangeValue exchangeValueRes = exchangeValueRepository.save(exchangeValue);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    	.path("/{id}").buildAndExpand(exchangeValueRes.getId()).toUri();
    	return ResponseEntity.created(location).build();
	}
    
    @PostMapping("/update-currency-exchange")
   	public ResponseEntity<Object> updateRetrieveExchangeValue(@RequestBody ExchangeValue exchangeValue ) {
       	
       	ExchangeValue exchangeValueRes = exchangeValueRepository.save(exchangeValue);
       	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
       	.path("/{id}").buildAndExpand(exchangeValueRes.getId()).toUri();
       	return ResponseEntity.created(location).build();
   	}
	
	@GetMapping("/fault-torelence-example")
	@HystrixCommand(fallbackMethod = "fallBackRetrieveExchangeValue")
	public ExchangeValue retrieveFaulthExchangeValue() {
		 
		throw new RuntimeException("not available");
	}
	
	public ExchangeValue fallBackRetrieveExchangeValue() {
		return new ExchangeValue(1000L,"INR","INR",BigDecimal.valueOf(65));
	}
	

}
