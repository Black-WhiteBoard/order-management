package com.fresh.freshmart.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fresh.freshmart.entity.Item;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductConsumer {

    @RetryableTopic(attempts ="10")
    @KafkaListener(topics = "productsTopic",containerFactory = "readcommitted_getCustomListenerContainerFactory" ,groupId = "products-group-3")
    public void processMessage(GenericMessage<String> msg) {
        log.info("Message received {} :",msg);
log.info("Product consumer processed msg : {}",msg.getPayload());

        ObjectMapper mapper =new ObjectMapper();
        try {
            Item item =  mapper.readValue(msg.getPayload(), Item.class);
              throw  new RuntimeException("simulated error in consumer");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


   @DltHandler
    public void dltLetterTopicHandler(ConsumerRecord<String,Object> failedMsg, @Header(KafkaHeaders.RECEIVED_TOPIC)  String topic){
log.error("Received message in Dead Letter Topic : {} from TOPIC:  {}",failedMsg,topic);
    }

}
