package com.deepak.controllers;

import com.deepak.Person;
import com.deepak.producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {
    
    @Mock
    Producer producer;
    
    @InjectMocks
    PersonController personController;
    
    @Test
    public void shouldCreatePerson() {
        Person person = new Person("Deepak", "Mehra");
        Mockito.doNothing().when(producer).send(person);
        ResponseEntity<Mono<Map<String, String>>> actualResult = personController.createPerson(person);
        assertNotNull(actualResult);
        int statusCode = actualResult.getStatusCode().value();
        assertEquals(201, statusCode);
        Map<String, String> result = actualResult.getBody().block();
        assertNotNull(result);
        assertEquals("Person Deepak Mehra successfully created.", result.get("success"));
    }
}
