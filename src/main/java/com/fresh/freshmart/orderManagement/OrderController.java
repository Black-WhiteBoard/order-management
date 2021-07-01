package com.fresh.freshmart.orderManagement;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.vavr.control.Try;

@EnableFeignClients
@RestController
public class OrderController {
	
	@Autowired
	private RestTemplate resttemplate;
	
	@Autowired
	private BulkheadConfig bulkheadConfig;
	
	@Autowired
	private PaymentServiceInt paymentServiceInt;
	
	@RequestMapping("/order/{type}")
	public ResponseEntity<String> processOrder(@PathVariable(name = "type") String type){
			
			 if(type.equals("swiggy")) {
				 Bulkhead bulkhead	=Bulkhead.of("swiggy",bulkheadConfig);
				  Supplier<String> supplier=Bulkhead.decorateSupplier(bulkhead, ()->swiggy());
				Try<String>  result =Try.ofSupplier(supplier);
				  
				  if(result.isSuccess()) {
					  return ResponseEntity.ok(result.get());
				  }else {
					  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Service is down.Plese try again later!");
				  }
			  }else if(type.equals("zomato")) {
				  Bulkhead bulkhead	=Bulkhead.of("zomato",bulkheadConfig);
				  Supplier<String> supplier=Bulkhead.decorateSupplier(bulkhead, ()->zomato());
					Try<String>  result =Try.ofSupplier(supplier);
					  
					  if(result.isSuccess()) {
						  return ResponseEntity.ok(result.get());
					  }else {
						  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Service is down.Plese try again later!");
					  }
				  
			  }
			  else  if(type.endsWith("foodpanda")) {
				  Bulkhead bulkhead	=Bulkhead.of("foodpanda",bulkheadConfig);
				  Supplier<String> supplier=Bulkhead.decorateSupplier(bulkhead, ()->foodpanda());
					Try<String>  result =Try.ofSupplier(supplier);
					  
					  if(result.isSuccess()) {
						  return ResponseEntity.ok(result.get());
					  }else {
						  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Service is down.Plese try again later!");
					  }
			  }
		return null;
	}
	
	
	public String swiggy() {
		System.out.println("***swiggy called*****");
		//String paymentUrl="http://localhost:8081/payment/paybill";
		//return resttemplate.getForObject(URI.create(paymentUrl), String.class);
		return paymentServiceInt.pay();
	}
	public String zomato() {
		try {
			Thread.currentThread().sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("***zomato called*****");
		String paymentUrl="http://localhost:8081/payment/paybill";
		return resttemplate.getForObject(URI.create(paymentUrl), String.class);
	}
	public String foodpanda() {
		System.out.println("***foodpanda called*****");
		try {
			Thread.currentThread().sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String paymentUrl="http://localhost:8081/payment/paybill";
		return resttemplate.getForObject(URI.create(paymentUrl), String.class);
	}

}
