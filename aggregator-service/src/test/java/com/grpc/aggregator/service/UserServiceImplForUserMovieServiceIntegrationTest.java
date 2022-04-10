package com.grpc.aggregator.service;

import com.grpc.common.Genre;
import com.grpc.user.UserGenreUpdateRequest;
import com.grpc.user.UserResponse;
import com.grpc.user.UserSearchRequest;
import com.grpc.user.UserServiceGrpc.UserServiceImplBase;

import io.grpc.stub.StreamObserver;

public class UserServiceImplForUserMovieServiceIntegrationTest extends UserServiceImplBase{

	@Override
	public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
		UserResponse userResponse = UserResponse.newBuilder()
				.setGenre(Genre.ACTION)
				.build();
		
		responseObserver.onNext(userResponse);
		responseObserver.onCompleted();
	}

	@Override
	public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
		// TODO Auto-generated method stub
		super.updateUserGenre(request, responseObserver);
	}

}
