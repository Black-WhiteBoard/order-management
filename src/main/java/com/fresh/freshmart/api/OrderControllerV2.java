package com.fresh.freshmart.api;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.fresh.freshmart.model.OrderCategoryCount;
import com.fresh.freshmart.repository.OrderRepoBase;
import com.fresh.freshmart.service.OrderService;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;

@Slf4j
@RestController
@RequestMapping("/v2/order")
public class OrderControllerV2 {
	
	@Autowired
	private RestTemplate resttemplate;
	
	@Autowired
	private CircuitBreaker circuitBreaker;

	/*@Autowired
	private OrderRepository orderRepository;*/

	@Autowired
	private MongoClient mongoClient;

	@Autowired
	private OrderService orderService;


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

	@GetMapping("/categorymap")
	public ResponseEntity<List<OrderCategoryCount>> getOrderCategoryDetails(){
        List<OrderCategoryCount> response=orderService.getOrderCategoryDetails();
		return   ResponseEntity.ok(response);
	}
	
}
