package com.grpc.aggregator.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.grpc.aggregator.dto.RecommendedMovie;
import com.grpc.aggregator.dto.UserGenre;
import com.grpc.common.Genre;
import com.grpc.movie.MovieSearchRequest;
import com.grpc.movie.MovieSearchResponse;
import com.grpc.movie.MovieServiceGrpc;
import com.grpc.user.UserGenreUpdateRequest;
import com.grpc.user.UserResponse;
import com.grpc.user.UserSearchRequest;
import com.grpc.user.UserServiceGrpc;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class UserMovieService {

	@GrpcClient("user-service")
	private UserServiceGrpc.UserServiceBlockingStub userStub;
	
	@GrpcClient("movie-service")
	private MovieServiceGrpc.MovieServiceBlockingStub movieStub;
	
	public List<RecommendedMovie> getUserMovieSuggestion(String loginId) {
		UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder()
				.setLoginId(loginId).build();
		
		UserResponse userResponse =  this.userStub.getUserGenre(userSearchRequest);
		
		MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder()
				.setGenre(userResponse.getGenre()).build();
		
		MovieSearchResponse movieSearchResponse = this.movieStub.getMovies(movieSearchRequest);
		
		return movieSearchResponse.getMoviesList()
		.stream()
		.map(movieDto -> new RecommendedMovie(
				movieDto.getTitle(),
				movieDto.getYear(),
				movieDto.getRating()
				)).collect(Collectors.toList());
	}
	
	public void setUserGenre(UserGenre userGenre) {
		UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
				.setLoginId(userGenre.getLoginId())
				.setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
				.build();
		
		this.userStub.updateUserGenre(userGenreUpdateRequest);
	}
}
