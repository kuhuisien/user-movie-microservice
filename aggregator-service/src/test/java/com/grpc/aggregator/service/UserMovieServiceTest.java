package com.grpc.aggregator.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest(properties = {
        "grpc.server.inProcessName=test", // Enable inProcess server
        "grpc.server.port=-1", // Disable external server
        "grpc.client.user-service.address=in-process:test", // Configure the client to connect to the inProcess server
        "grpc.client.movie-service.address=in-process:test"
        }) 
@SpringJUnitConfig(MyComponentIntegrationTestConfiguration.class)
// Spring doesn't start without a config (might be empty)
@DirtiesContext // Ensures that the grpc-server is properly shutdown after each test
        // Avoids "port already in use" during tests
public class UserMovieServiceTest {

	@Autowired
    private UserMovieService userMovieService;

    @Test
    @DirtiesContext
    public void getUserMovieSuggestionTest() {
    	System.out.println(userMovieService);
    	//List<RecommendedMovie> movieList = userMovieService.getUserMovieSuggestion("testId");
    	
    	//assertEquals(movieList.size(), 2);
    }
}
