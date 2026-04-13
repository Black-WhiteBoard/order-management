package com.fresh.freshmart.orderservice;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fresh.freshmart"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@EnableJpaRepositories
@EnableMongoRepositories(basePackages = { "com.fresh.freshmart.repository"})
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
	
		CircuitBreakerConfig circuitBreakecofig= CircuitBreakerConfig.custom().failureRateThreshold(50).permittedNumberOfCallsInHalfOpenState(2).minimumNumberOfCalls(6).maxWaitDurationInHalfOpenState(Duration.ofMillis(2000)).waitDurationInOpenState(Duration.ofMinutes(10)).build();
		CircuitBreaker circuitBreaker=	CircuitBreaker.of("orderservice", circuitBreakecofig);
		return circuitBreaker;
	}
	
	@Bean
	public Bulkhead getBulkhead() {
		BulkheadConfig bulkheadConfig =BulkheadConfig.custom().maxConcurrentCalls(4).maxWaitDuration(Duration.ofMillis(60000)).build();
		Bulkhead bulkhead	=Bulkhead.of("orderServiceBulkhead",bulkheadConfig);
		return bulkhead;
		
	}
}
