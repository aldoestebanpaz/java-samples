# Samples of testing on Java

NOTE: This project was created using java 11 and Spring Boot 2.7.3.

## How I created this project

Go to [Spring Initializr](https://start.spring.io/) and create a project including the "Spring Web" and "Lombok" dependency.

## How to run the tests

### Run all tests

Move to the tests folder and run:

```sh
./mvnw test
```

Or, to generate a new build and test:

```sh
./mvnw clean test
```

### Run single test case

Run one of the following commands in this top directory.

Option 1: An specific test class, with all the methods inside.

```sh
./mvnw test -Dtest="TestClassName"
```

Option 2: An specific test method.

```sh
./mvnw test -Dtest="TestClassName#TestMethodName"
```

Example:

```sh
./mvnw test -Dtest="TodosApiServiceTest#testFetchTodosShouldReturnAList"
```
