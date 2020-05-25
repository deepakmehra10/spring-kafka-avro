package com.deepak.controllers;

import com.deepak.Person;
import com.deepak.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PersonController {
    
    @Autowired
    Producer producer;
    
    @PostMapping
    public ResponseEntity<Mono<Map<String, String>>> createPerson(@RequestBody Person person) {
        producer.send(person);
        Map<String, String> result = new HashMap<>();
        result.put("success", "Person " + person.getFirstName()
                + " " + person.getLastName() + " successfully created.");
        return ResponseEntity.status(HttpStatus.CREATED).body(Mono.just(result));
    }
}
