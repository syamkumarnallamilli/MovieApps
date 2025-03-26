package MovieApp.MovieApp.Actuator;

import MovieApp.MovieApp.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Endpoint(id = "movieCount") 
@Component
public class MovieCountEndpoint {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieCountEndpoint(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @ReadOperation  
    public String countMovies() {
        long count = movieRepository.count();  // Count the number of movies
        return "Total Movies: " + count;  // Return the count as a string
    }
}

// In Spring Boot, Actuator is a module that provides production-ready features to help you monitor and manage your application. It adds various management endpoints to your application, allowing you to check the application's health, metrics, application environment, and other operational information.

// Key Features of Spring Boot Actuator:
// Health Checks: Actuator provides endpoints to check the health of your application. It can check the status of your database connections, message queues, and other services.

// Metrics: It provides a wide range of metrics about the application, including memory usage, garbage collection, active threads, and HTTP requests. You can access metrics via the /actuator/metrics endpoint.

// Environment Information: You can view information about your application’s environment, including system properties and configuration properties, through the /actuator/env endpoint.

// Application Information: It can provide information about your application, such as version and description, which you can configure in your application.properties or application.yml.

// Custom Endpoints: You can create your own custom management endpoints if the provided ones do not meet your requirements.

// Security: You can secure the actuator endpoints using Spring Security to restrict access based on roles.