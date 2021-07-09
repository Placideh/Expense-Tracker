package com.placide.tracker.repositories;

import org.springframework.stereotype.Repository;

import com.placideh.tracker.domain.User;
import com.placideh.tracker.exceptions.EtAuthException;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private static final String SQL_CREATE="INSERT INTO ET_USERS (USER_ID,FIRST_NAME ,LAST_NAME,EMAIL, PASSWORD)VALUES (?,?,?,?)";

	@Override
	public Integer create(String firstName, String lastName, String eamil, String password) throws EtAuthException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByEmailAndPassword(String email, String password) throws EtAuthException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
