package com.grpc.aggregator.service;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;

@Configuration
@ImportAutoConfiguration({
        GrpcServerAutoConfiguration.class, // Create required server beans
        GrpcServerFactoryAutoConfiguration.class, // Select server implementation
        GrpcClientAutoConfiguration.class}) // Support @GrpcClient annotation*/
public class MyComponentIntegrationTestConfiguration {
	@Bean
    UserMovieService userMovieService() {
        return new UserMovieService();
    }

    @Bean
    MovieServiceImplForUserMovieServiceIntegrationTest movieServiceImpl() {
        return new MovieServiceImplForUserMovieServiceIntegrationTest();
    }
    
    @Bean
    UserServiceImplForUserMovieServiceIntegrationTest userServiceImpl() {
        return new UserServiceImplForUserMovieServiceIntegrationTest();
    }

}
