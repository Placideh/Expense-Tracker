package com.placideh.tracker.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.placideh.tracker.domain.User;
import com.placideh.tracker.exceptions.EtAuthException;


@Repository
public class UserRepositoryImpl implements UserRepository {
	private static final String SQL_CREATE="INSERT INTO et_users (first_name ,last_name,email, password)VALUES (?,?,?,?)";
	private static final String SQL_COUNT_BY_EMAIL="SELECT COUNT(*) FROM et_users WHERE email=?";
	private static final String SQL_FIND_BY_ID="SELECT * FROM et_users WHERE user_id=?";
	private static final String SQL_FIND_BY_EMAIL="SELECT * FROM et_users WHERE email=?";
	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unused")
	@Override
	public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
		// TODO Auto-generated method stub
		String hashedPassword=BCrypt.hashpw(password, BCrypt.gensalt(10));
		try {
			KeyHolder keyHolder=new GeneratedKeyHolder();
			jdbcTemplate.update(connection->{
				PreparedStatement ps =connection.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1,firstName);
				ps.setString(2,lastName);
				ps.setString(3,email);
				ps.setString(4,hashedPassword);
				return ps;
				
			},keyHolder);
			return (Integer) keyHolder.getKey().intValue();
		}catch(Exception e) {
			throw  new EtAuthException("invalid details. Failed to create an account");
			
		}
		
	}

	@Override
	public User findByEmailAndPassword(String email, String password) throws EtAuthException {
		// TODO Auto-generated method stub
		try {
			User user=jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email},userRowMapper);
			if(!BCrypt.checkpw(password, user.getPassword()))
				throw new  EtAuthException("invalid email/password");
			return user;
		}catch(EmptyResultDataAccessException e) {
			throw new  EtAuthException("invalid email/password");
		}
	}

	@Override
	public Integer getCountByEmail(String email) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,new Object[] {email},Integer.class);
	}

	@Override
	public User findById(Integer userId) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {userId},userRowMapper);
	}
	private RowMapper<User> userRowMapper=((ResultSet rs,int rowNum)->{
		return new User(rs.getInt("user_id"),
			rs.getString("first_name"),
			rs.getString("last_name"),
			rs.getString("email"),
			rs.getString("password"));
	});
}
