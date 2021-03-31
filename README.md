# TRANSACTION MICROSERVICE

This service is in charge of managing transactions

## How to Run

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository
* Create Mysql database
  
    - run `com/nnamdi/transaction/data/transaction.sql`
    
* Make sure you are using JDK 1.8 and Gradle 5.x
* Change mysql username and password as per your installation
    + open `src/main/resources/application.properties`
    + change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation
* You can build the project and run the tests by running ```gradle clean build```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar build/libs/transaction-0.0.1-SNAPSHOT.jar
```


Once the application runs you should see something like this

```
2017-08-29 17:31:23.091  INFO 19387 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8090 (http)
2017-08-29 17:31:23.097  INFO 19387 --- [           main] com.khoubyari.example.Application        : Started Application in 22.285 seconds (JVM running for 23.032)
```

## About the Service

The service is just a simple transaction REST service. It uses a mysql database to store the data. You can also use other relational database like PostgreSQL. If your database connection properties work, you can call some REST endpoints defined in ```package com.nnamdi.transaction.controller``` on **port 9000**. (see below)

More interestingly, you can start calling some operational endpoints (see full list below) like ```/deposit``` and ```/withdraw``` (these are available on **port 9000**)

You can use this sample service to understand the conventions and configurations that allow you to create a DB-backend RESTful service. Once you understand and get comfortable with the sample app you can add your own services following the same patterns as the sample service.


Here are some endpoints you can call:

### Deposit to Account

```
http://localhost:9000/api/deposit
```

### Withdrawal from Account

```
http://localhost:9000/api/withdraw
```

### Generate transaction

```
http://localhost:9000/api/transactions/all
http://localhost:9000/api/transactions/transaction/{id}

```


### Deposit to account payload

```
POST /api/deposit
Accept: application/json
Content-Type: application/json

{
    "transactionType":1,
    "amount": 2000.00,
    "transactionDescription": "Deposit to Wallet",
    "accountId":12345

}

RESPONSE: HTTP 201 (Created)
{
    "transactionId": 16,
    "transactionType": 1,
    "amount": 2000.0,
    "transactionDescription": "Deposit to Wallet",
    "accountId": 12345,
    "date": null,
    "_links": {
        "self": {
            "href": "http://localhost:9001/api/transactions/transaction/16"
        },
        "transaction": {
            "href": "http://localhost:9001/api/transactions/all"
        }
    }
}
```

### Withdraw from Account Payload


```
POST /api/deposit
Accept: application/json
Content-Type: application/json

{
    "transactionType":1,
    "amount": 2000.00,
    "transactionDescription": "Deposit to Wallet",
    "accountId":12345

}

RESPONSE: HTTP 200 
{
    "transactionId": 14,
    "transactionType": 2,
    "amount": 2000.0,
    "transactionDescription": "Deposit to Wallet",
    "accountId": 12345,
    "date": null,
    "_links": {
        "self": {
            "href": "http://localhost:9001/api/transactions/transaction/14"
        },
        "transaction": {
            "href": "http://localhost:9001/api/transactions/all"
        }
    }
}
```
### To view Swagger 2 API docs

Run the server and browse to localhost:9000/swagger-ui.html

# About Spring Boot

Spring Boot is an "opinionated" application bootstrapping framework that makes it easy to create new RESTful services (among other types of applications). It provides many of the usual Spring facilities that can be configured easily usually without any XML. In addition to easy set up of Spring Controllers, Spring Data, etc. Spring Boot comes with the Actuator module that gives the application the following endpoints helpful in monitoring and operating the service:



# Area of Improvement

**1. Setting up notification on successful payment - Partly completed**

**2. Implementing JWT Authentication**


# Questions and Comments: nwabuokeinnamdi19@gmail.com

