# Testing with Mockito and JUnit in Spring Boot

Testing in Spring Boot can be efficiently done using JUnit for unit testing and Mockito for mocking dependencies. In this tutorial, we will learn how to write test cases for the `MovieService` and `MovieController` classes in the Movie API project.

## Tools and Dependencies

- **JUnit**: The most popular testing framework in Java.
- **Mockito**: A mocking framework to simulate the behavior of real objects in a controlled way.
- **Spring Boot Test**: Provides integration for Spring testing with JUnit.

Add the following dependencies to your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>5.5.0</version> <!-- Latest stable version -->
        <scope>test</scope>
    </dependency>

    <!-- Optional: Mockito with JUnit Jupiter (for JUnit 5) -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
        <version>5.5.0</version>
        <scope>test</scope>
    </dependency>

    <!-- Spring Boot Test Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>