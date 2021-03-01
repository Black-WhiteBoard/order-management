package com.fresh.freshmart.orderManagement;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnCallNotPermittedEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnFailureRateExceededEvent;
import io.vavr.control.Try;

@RestController
@RequestMapping("/product")
public class OrderService {
	
	@Autowired
	private RestTemplate resttemplate;
	
	@Autowired
	private CircuitBreaker circuitBreaker;

	@RequestMapping(method = RequestMethod.GET ,path = "/getProducts" )
	public  ResponseEntity<String> Products(){
		
		ResponseEntity<String> response=null;
		 Supplier<String>  supplier= circuitBreaker.decorateSupplier(()->dopaymentForOrder());
		 Try<String> trysupplier=  Try.ofSupplier(supplier);
		  try {
			  response=ResponseEntity.ok(trysupplier.get());
		      return  response;
		  }catch(Exception e) {
			  System.out.println("Circuit OPEN **************************");
			  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server is down ,Please Try again later !") ;
		  }
	}
	
	public String dopaymentForOrder() {
		System.out.println("***payment service called*****");
		String paymentUrl="http://localhost:8081/payment/paybill";
		return resttemplate.getForObject(URI.create(paymentUrl), String.class);
	}
	
	public CompletableFuture<ResponseEntity<String>> doPayment(){
		return CompletableFuture.supplyAsync(()->dopaymentForOrder()).handle((value,ex) -> {
			ResponseEntity<String> response=null;
			  if(value!=null)
				  response =ResponseEntity.ok(value);
			   if(ex!=null)
				   response =ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Order Service is down"); 
			return response;
	});
		
	}
	
}
