package com.grpc.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.grpc.movie.MovieDto;
import com.grpc.movie.MovieSearchRequest;
import com.grpc.movie.MovieSearchResponse;
import com.grpc.movie.MovieServiceGrpc.MovieServiceImplBase;
import com.grpc.movie.repository.MovieRepository;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class MovieService extends MovieServiceImplBase{
	
	@Autowired
	private MovieRepository repository;

	@Override
	public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
		String genre = request.getGenre().toString();
		
		List<MovieDto> movieDtoList = this.repository.getMovieByGenreOrderByYearDesc(genre)
		.stream()
		.map(movie -> MovieDto.newBuilder()
				.setTitle(movie.getTitle())
				.setYear(movie.getYear())
				.setRating(movie.getRating()).build())
		.collect(Collectors.toList());
		
		MovieSearchResponse movieSearchResponse = 
				MovieSearchResponse.newBuilder().addAllMovies(movieDtoList).build();
		
		responseObserver.onNext(movieSearchResponse);
		responseObserver.onCompleted();
	}
	
}
