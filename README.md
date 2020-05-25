# spring-kafka-avro
This repository talks about the Kafka producer and consumer with avro messages.

##### Prerequisites

* Java Development Kit (JDK), version 8 or higher.

* Maven

* Confluent Kafka

##### Getting the Project
https://github.com/deepakmehra10/spring-kafka-avro.git

##### Command to start the project

Step1. **Start Confluent Kafka**
Step2. **mvn spring-boot:run**

Json format for creating a Person is mentioned below :

**1. Create Person:**

> Route(Method - POST) : localhost:9000/api/persons

Rawdata(json): {"firstName": "Mishty", "lastName": "Mehra"}

Consumer will be started as soon as the application goes up, once the record is produced using above API, consumer 
will consumer the record and the log will be displayed on the console.

