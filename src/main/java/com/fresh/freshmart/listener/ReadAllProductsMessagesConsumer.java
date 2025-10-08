package com.fresh.freshmart.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class ReadAllProductsMessagesConsumer {


    @KafkaListener(topics = "productsTopic",containerFactory = "readuncommitted_getCustomListenerContainerFactory",groupId = "products-group-4")
    public void processMessage(String msg) {
log.info("Product consumer processed msg : {}",msg);
    }


}
