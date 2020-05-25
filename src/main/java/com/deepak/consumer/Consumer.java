package com.deepak.consumer;

import com.deepak.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    
    @KafkaListener(topics = {"${kafka.topic}"})
    public void receive(Person person) {
        LOGGER.info("Consuming Person record with values {}, {}", person.getFirstName(), person.getLastName());
    }
}
