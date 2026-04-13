package com.fresh.freshmart.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

//@Service
@Log4j2
public class ReadAllProductsMessagesConsumer {


    @KafkaListener(topics = "products-topic",containerFactory = "readuncommitted_getCustomListenerContainerFactory",groupId = "OM-CUSTOMER-GRP")
    public void processMessage(GenericMessage<String> payload) {
log.info("Order  Management Consumer 2, processed msg : {}",payload);
    }


}
