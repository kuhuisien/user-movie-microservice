package com.grpc.aggregator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grpc.aggregator.dto.RecommendedMovie;
import com.grpc.aggregator.dto.UserGenre;
import com.grpc.aggregator.service.UserMovieService;

@RestController
public class AggregatorController {
	
	@Autowired
	private UserMovieService userMovieService;
	
	@GetMapping("/user/{loginId}")
	public List<RecommendedMovie> getMovies(@PathVariable String loginId) {
		return this.userMovieService.getUserMovieSuggestion(loginId);
	}
	
	@PutMapping("/user") 
	public void setUserGenre(@RequestBody UserGenre usergenre) {
		this.userMovieService.setUserGenre(usergenre);
	}

}
