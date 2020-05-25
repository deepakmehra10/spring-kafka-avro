package com.deepak.producer;

import com.deepak.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    
    @Value("${kafka.topic}")
    private String avroTopic;
    
    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;
    
    public void send(Person user) {
        LOGGER.info("sending user='{}'", user.toString());
        kafkaTemplate.send(avroTopic, user);
    }
}
