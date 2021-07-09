package com.fresh.freshmart.orderManagement;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("payment-service")
@RequestMapping("/payment")
public interface PaymentServiceInt {
   @RequestMapping("/paybill")
   public void pay();
   public String receive();
   public void balance();
}
