package com.deepak.producer;

import com.deepak.Person;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProdcuerConfig {
    
    @Value("${spring.kafka.bootstrap-servers}")
        private String bootstrapServers;
        
        @Bean
        public Map<String, Object> producerConfigs() {
            Map<String, Object> props = new HashMap<>();
            
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
            props.put("schema.registry.url", "http://localhost:8081");
            
            return props;
        }
        
        @Bean
        public ProducerFactory<String, Person> producerFactory() {
            return new DefaultKafkaProducerFactory<>(producerConfigs());
        }
        
        @Bean
        public KafkaTemplate<String, Person> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
        }
        
        
    
}
