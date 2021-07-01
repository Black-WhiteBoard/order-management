package com.fresh.freshmart.orderservice;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

@Configuration
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = { "com.fresh"})
public class OrderserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderserviceApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CircuitBreaker circuitBreaker() {
	
		CircuitBreakerConfig circuitBreakecofig= CircuitBreakerConfig.custom().failureRateThreshold(50).permittedNumberOfCallsInHalfOpenState(2).minimumNumberOfCalls(6).maxWaitDurationInHalfOpenState(Duration.ofMillis(60000)).build();
		CircuitBreaker circuitBreaker=	CircuitBreaker.of("orderservice", circuitBreakecofig);
		return circuitBreaker;
	}
	
	@Bean
	public BulkheadConfig bulkheadConfig() {
		BulkheadConfig bulkheadConfig =BulkheadConfig.custom().maxConcurrentCalls(4).maxWaitDuration(Duration.ofMillis(60000)).build();
		return bulkheadConfig;
		
	}
}
