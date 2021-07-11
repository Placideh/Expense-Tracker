package com.placideh.tracker.repositories;

import com.placideh.tracker.domain.User;
import com.placideh.tracker.exceptions.EtAuthException;

public interface UserRepository {
	Integer create(String firstName,String lastName,String eamil,String password) throws EtAuthException;
	User findByEmailAndPassword(String email,String password) throws EtAuthException;
	Integer getCountByEmail(String email);
	User findById(Integer userId);
}
