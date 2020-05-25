package com.deepak.producer;

import com.deepak.Person;
import com.deepak.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest()
public class ProducerTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerTest.class);
    
    private static String TOPIC_NAME = "testnow";
    
    @Autowired
    private Producer producer;
    
    @Autowired
    private Consumer consumer;
    
    private KafkaMessageListenerContainer<String, Person> container;
    
    private BlockingQueue<ConsumerRecord<String, Person>> consumerRecords;
    
    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME);
    
    @Before
    public void setUp() {
        consumerRecords = new LinkedBlockingQueue<>();
        
        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);
        
        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps(
                "sender", "false", embeddedKafka.getEmbeddedKafka());
        
        DefaultKafkaConsumerFactory<String, Person> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);
        
        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
        ConsumerRecord<String, Person> consumerRecord = new ConsumerRecord<>(TOPIC_NAME, 1, 1, "key", new Person("Deepak", "Mehra"));
        consumerRecords.add(consumerRecord);
        container.setupMessageListener((MessageListener<String, Person>) record -> {
            LOGGER.debug("Listened message='{}'", record.toString());
            consumerRecords.add(record);
        });
        container.start();
        
        System.out.println(consumerRecords.size() + "\n\n\n\n\n\nn\n\n\n\n\n\n");
        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
    }
    
    @After
    public void tearDown() {
        container.stop();
    }
    
    @Test
    public void shouldCreatePerson() throws InterruptedException {
        Person person = new Person("Deepak", "Mehra");
        producer.send(person);
        
        ConsumerRecord<String, Person> received = consumerRecords.poll(20, TimeUnit.SECONDS);
        
        assertNotNull(received.value());
        Person actualResult = received.value();
        assertEquals("Deepak", actualResult.getFirstName());
        assertEquals("Mehra", actualResult.getLastName());
    }
}
