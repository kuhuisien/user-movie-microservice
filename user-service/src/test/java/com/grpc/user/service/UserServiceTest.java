package com.grpc.user.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import com.grpc.common.Genre;
import com.grpc.user.UserGenreUpdateRequest;
import com.grpc.user.UserResponse;
import com.grpc.user.UserSearchRequest;
import com.grpc.user.entity.User;
import com.grpc.user.repository.UserRepository;

import io.grpc.stub.StreamObserver;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	StreamObserver<UserResponse> userResponseMock = mock(StreamObserver.class);
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		User user = new User();
		user.setLoginId("dummyLoginId");
		user.setName("dummyName");
		user.setGenre(Genre.ACTION.toString());
		
		when(repository.findById("dummyLoginId")).thenReturn(Optional.of(user));
	}

	@Test
	void getUserGenreTest() {
		UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder()
				.setLoginId("dummyLoginId").build();
		
		userService.getUserGenre(userSearchRequest, userResponseMock);
		
		UserResponse expectedUserResponse = UserResponse.newBuilder()
				.setLoginId("dummyLoginId")
				.setName("dummyName")
				.setGenre(Genre.valueOf("ACTION"))
				.build();
		
		verify(userResponseMock).onNext(expectedUserResponse);
		verify(userResponseMock).onCompleted();
	}
	
	@Test
	void updateUserGenre() {
		UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
				.setLoginId("dummyLoginId")
				.setGenre(Genre.WESTERN)
				.build();
		
		userService.updateUserGenre(userGenreUpdateRequest, userResponseMock);
		
		UserResponse expectedUserResponse = UserResponse.newBuilder()
				.setLoginId("dummyLoginId")
				.setName("dummyName")
				.setGenre(Genre.valueOf("WESTERN"))
				.build();
		
		verify(userResponseMock).onNext(expectedUserResponse);
		verify(userResponseMock).onCompleted();
	}

}
