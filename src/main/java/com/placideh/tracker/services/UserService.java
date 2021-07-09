package com.placideh.tracker.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.placideh.tracker.domain.User;
import com.placideh.tracker.exceptions.EtAuthException;
//when it is not authorized this will return Http status which 401

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public interface UserService {
	User validateUser(String email,String password) throws EtAuthException;
	User registerUser(String firstName,String lastName,String email,String password) throws EtAuthException;
}
