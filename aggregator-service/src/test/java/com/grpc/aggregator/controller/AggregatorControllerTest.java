package com.grpc.aggregator.controller;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grpc.aggregator.dto.UserGenre;
import com.grpc.aggregator.service.UserMovieService;
import com.grpc.common.Genre;

@RunWith(SpringRunner.class)
@WebMvcTest(AggregatorController.class)
public class AggregatorControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserMovieService mockService;
	
	@Test
	public void getMoviesTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/user/testId")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request);
		
		verify(mockService).getUserMovieSuggestion("testId");
		
	}

	@Test
	public void UpdateUserGenreTest() throws Exception {
		UserGenre userGenre = new UserGenre();
		userGenre.setLoginId("testId");
		userGenre.setGenre(Genre.ACTION.toString());;
		
		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(userGenre);
		
		RequestBuilder request = MockMvcRequestBuilders.put("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson);
		
		mockMvc.perform(request);
		
		verify(mockService).setUserGenre(userGenre);
	}

}
