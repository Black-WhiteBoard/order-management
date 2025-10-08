package com.fresh.freshmart.configuration;

import com.fresh.freshmart.entity.Item;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this configuration is needed only when multiple consumers are there with each having difference  kafka configuration
 *  i.e each consumer are consuming messages from different kafka servers
 */
@Configuration
public class CustomListenerContainerFactory {



    @Value("${spring.kafka.bootstrap-servers}")
    private List<String> bootStrapServerUrl;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private  String valueSerializer;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private  String keySerializer;

    @Bean
    public  KafkaTemplate<String,Item>  kafkaTemplate(ProducerFactory<String,Item> producerFactoryForDtl){
        return new KafkaTemplate<>(producerFactoryForDtl);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Item> readcommitted_getCustomListenerContainerFactory(KafkaTemplate<String,Item> kafkaTemplate){

     ConcurrentKafkaListenerContainerFactory<String, Item> listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(readCommitted_customConsumerFactory());

        DeadLetterPublishingRecoverer deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
       // deadLetterPublishingRecoverer

        FixedBackOff backOff = new FixedBackOff(10000, 5);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(deadLetterPublishingRecoverer);
        listenerContainerFactory.setCommonErrorHandler(errorHandler);
        return  listenerContainerFactory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Item> readuncommitted_getCustomListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, Item> listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();

        listenerContainerFactory.setConsumerFactory(readUncommitted_customConsumerFactory());

        return  listenerContainerFactory;
    }

    @Bean
    public ConsumerFactory<String, Item> readCommitted_customConsumerFactory() {
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServerUrl);
       // config.put(ConsumerConfig.GROUP_ID_CONFIG,consumerGroupId);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueSerializer);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keySerializer);
        config.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,"read_committed");
        JsonDeserializer<Item> jsonDeserializer = new JsonDeserializer<>(Item.class);
        jsonDeserializer.addTrustedPackages("com.fresh.freshmart.entity");

        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),jsonDeserializer);
    }

    @Bean
    public ProducerFactory<String,Item> producerFactoryForDtl(){
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServerUrl);
        // config.put(ConsumerConfig.GROUP_ID_CONFIG,consumerGroupId);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,valueSerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,keySerializer);
        //config.put(ProducerConfig.,"read_committed");
        JsonSerializer<Item> jsonserializer = new JsonSerializer<>();
       //jsonserializer.se("com.fresh.freshmart.entity");

        return new DefaultKafkaProducerFactory<String,Item>(config, new StringSerializer(),jsonserializer);
    }


    @Bean
    public ConsumerFactory<String, Item> readUncommitted_customConsumerFactory() {
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServerUrl);
        // config.put(ConsumerConfig.GROUP_ID_CONFIG,consumerGroupId);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueSerializer);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keySerializer);
        config.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,"read_uncommitted");

        JsonDeserializer<Item> jsonDeserializer = new JsonDeserializer<>(Item.class);
        jsonDeserializer.addTrustedPackages("com.fresh.freshmart.entity");
        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),jsonDeserializer);
    }


}
