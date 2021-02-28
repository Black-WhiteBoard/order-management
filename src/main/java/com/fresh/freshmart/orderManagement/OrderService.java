package com.fresh.freshmart.orderManagement;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/product")
public class OrderService {
	
	@Autowired
	private RestTemplate resttemplate;

	@RequestMapping(method = RequestMethod.GET ,path = "/getProducts" )
	public CompletableFuture<Object> Products(){
		return CompletableFuture.supplyAsync(()->dopaymentForOrder()).handle((value,ex) -> {
				ResponseEntity<String> response=null;
				  if(value!=null)
					  response =ResponseEntity.ok(value);
				   if(ex!=null)
					   response =ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server is down ,Please Try again later !"); 
				return response;
		});
	}
	
	public String dopaymentForOrder() {
		String paymentUrl="http://localhost:8081/payment/paybill";
		return resttemplate.getForObject(URI.create(paymentUrl), String.class);
	}
	
}
