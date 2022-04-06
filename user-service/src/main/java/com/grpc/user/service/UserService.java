package com.grpc.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.grpc.common.Genre;
import com.grpc.user.UserGenreUpdateRequest;
import com.grpc.user.UserResponse;
import com.grpc.user.UserSearchRequest;
import com.grpc.user.UserServiceGrpc.UserServiceImplBase;
import com.grpc.user.repository.UserRepository;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserService extends UserServiceImplBase{
	
	@Autowired
	private UserRepository repository;

	@Override
	public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
		String loginId = request.getLoginId();
		
		UserResponse.Builder userBuilder = UserResponse.newBuilder();
		
		this.repository.findById(loginId)
		.ifPresent(user -> {
			userBuilder.setName(user.getName())
			.setLoginId(user.getLoginId())
			.setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
		});;
		
		responseObserver.onNext(userBuilder.build());
		responseObserver.onCompleted();
	}

	@Override
	@Transactional
	public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
		String loginId = request.getLoginId();
		
		UserResponse.Builder userBuilder = UserResponse.newBuilder();
		
		this.repository.findById(loginId)
		.ifPresent(user -> {
			String genre = request.getGenre().toString();
			user.setGenre(genre);
			
			userBuilder.setName(user.getName())
			.setLoginId(user.getLoginId())
			.setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
		});
		
		responseObserver.onNext(userBuilder.build());
		responseObserver.onCompleted();
	}

}
