package com.placideh.tracker.repositories;

import java.util.List;

import com.placideh.tracker.domain.Transaction;
import com.placideh.tracker.exceptions.EtBadRequestException;
import com.placideh.tracker.exceptions.EtResourceNotFoundException;

public class TransactionRepositoryImpl implements TransactionRepository {

	@Override
	public List<Transaction> findAll(Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction findById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate)
			throws EtBadRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction)
			throws EtBadRequestException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		// TODO Auto-generated method stub
		
	}

}
