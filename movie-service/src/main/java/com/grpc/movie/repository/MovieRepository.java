package com.grpc.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grpc.movie.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>{
	
	List<Movie> getMovieByGenreOrderByYearDesc(String genre);

}
