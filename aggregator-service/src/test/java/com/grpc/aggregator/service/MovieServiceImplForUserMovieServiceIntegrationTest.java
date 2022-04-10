package com.grpc.aggregator.service;

import com.grpc.movie.MovieDto;
import com.grpc.movie.MovieSearchRequest;
import com.grpc.movie.MovieSearchResponse;
import com.grpc.movie.MovieServiceGrpc.MovieServiceImplBase;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class MovieServiceImplForUserMovieServiceIntegrationTest extends MovieServiceImplBase{

	@Override
	public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
		MovieDto movieDto = MovieDto.newBuilder().build();
		
		MovieSearchResponse response = MovieSearchResponse.newBuilder()
				.addMovies(movieDto)
				.addMovies(movieDto)
				.addMovies(movieDto)
				.build();
		
        responseObserver.onNext(response);
        
        responseObserver.onCompleted();
	}

}
