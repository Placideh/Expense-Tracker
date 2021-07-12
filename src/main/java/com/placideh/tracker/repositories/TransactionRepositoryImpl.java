package com.placideh.tracker.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.placideh.tracker.domain.Transaction;
import com.placideh.tracker.exceptions.EtBadRequestException;
import com.placideh.tracker.exceptions.EtResourceNotFoundException;
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
	private static final String SQL_CREATE="INSERT INTO et_transactions (categoryId,userId,amount,note,transaction_date,)values(?,?,?,?,?)";
	private static final String SQL_FIND_BY_ID="SELECT * FROM et_transactions where user_id=? and category_id =? and transaction_id=?";
	private static final String SQL_FIND_ALL ="SELECT * FROM et_transactions WHERE user_id=? AND category_id=?";
	private static final String SQL_UPDATE="UPDATE INTO et_transactions SET amount=?, note=? ,transation_date=? WHERE user_id=? "
			+ "and category_id=? and transaction_id=?";
	private static final String SQL_DELETE="DELETE FROM et_transactions WHERE user_id=? and category_id=? and transaction_id=?";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<Transaction> findAll(Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(SQL_FIND_ALL, new Object[] {userId,categoryId},transactionRowMapper);
	}

	@Override
	public Transaction findById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		try {
			return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {userId,categoryId,transactionId},transactionRowMapper);
		}catch(Exception e) {
			throw new EtResourceNotFoundException("Transaction not Found ");
		}
	}

	@Override
	public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate)
			throws EtBadRequestException {
		try {
			KeyHolder keyHolder=new GeneratedKeyHolder();
			jdbcTemplate.update(connection ->{
				PreparedStatement ps=connection.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1,categoryId);
				ps.setInt(2,userId);
				ps.setDouble(3,amount);
				ps.setString(4,note);
				ps.setLong(4, transactionDate);
				return ps;
			},keyHolder);
			return keyHolder.getKey().intValue();
			
		}catch(Exception e) {
			throw new EtBadRequestException("invalid request");
			
		}
	}

	@Override
	public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction)
			throws EtBadRequestException {

		try {
			jdbcTemplate.update(SQL_UPDATE,new Object[] {transaction.getAmount(),transaction.getNote(),transaction.getTransactionDate()}
			,userId,categoryId,transactionId);
			
		}catch(Exception e) {
			throw new EtBadRequestException("invalid request");
		}
	}

	@Override
	public void removeById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		
			int count=jdbcTemplate.update(SQL_DELETE,new Object[] {userId,categoryId,transactionId});
			if(count==0)
				throw new EtResourceNotFoundException("Transaction not found");
		
	}
	private RowMapper<Transaction> transactionRowMapper=((rs,rowNum)-> {
		return new Transaction (
				rs.getInt("transaction_id"),
				rs.getInt("category_id"),
				rs.getInt("user_id"),
				rs.getDouble("amount"),
				rs.getString("note"),
				rs.getLong("transaction_date")
				);
	});

}
