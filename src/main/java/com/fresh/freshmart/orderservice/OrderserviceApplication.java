package com.fresh.freshmart.orderservice;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

@SpringBootApplication
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
	
		CircuitBreakerConfig circuitBreakecofig= CircuitBreakerConfig.custom().failureRateThreshold(50).permittedNumberOfCallsInHalfOpenState(2).minimumNumberOfCalls(6).maxWaitDurationInHalfOpenState(Duration.ofMillis(2000)).build();
		CircuitBreaker circuitBreaker=	CircuitBreaker.of("orderservice", circuitBreakecofig);
		return circuitBreaker;
	}
	
	
}
