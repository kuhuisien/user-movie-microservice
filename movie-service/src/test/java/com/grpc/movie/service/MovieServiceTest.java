package com.grpc.movie.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import com.grpc.common.Genre;
import com.grpc.movie.MovieSearchRequest;
import com.grpc.movie.MovieSearchResponse;
import com.grpc.movie.entity.Movie;
import com.grpc.movie.repository.MovieRepository;

import io.grpc.stub.StreamObserver;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

	@InjectMocks
	private MovieService movieService;
	
	@Mock
	private MovieRepository repository;
	
	@Mock
	StreamObserver<MovieSearchResponse> movieResponseMock = mock(StreamObserver.class);
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		Movie movie = new Movie();
		movie.setTitle("testMovieTitle");
		
		List<Movie> movieDtoList = new ArrayList<>();
		
		// mock repository to return two Movie items
		movieDtoList.add(movie);
		movieDtoList.add(movie);
		
		when(repository.getMovieByGenreOrderByYearDesc("ACTION")).thenReturn(movieDtoList);
		
		
	}
	
	@Test
	void getMoviesTest() {
		MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder()
				.setGenre(Genre.ACTION).build();
		
		movieService.getMovies(movieSearchRequest, movieResponseMock);
		
		ArgumentCaptor<MovieSearchResponse> argument = 
				ArgumentCaptor.forClass(MovieSearchResponse.class);
		
		verify(movieResponseMock).onNext(argument.capture());
		
		// test to ensure response returns two MovieDto items
		assertEquals(argument.getValue().getMoviesList().size(), 2);
		
		verify(movieResponseMock).onCompleted(); 
	}
}
