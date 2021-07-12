package com.placideh.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placideh.tracker.domain.Transaction;
import com.placideh.tracker.exceptions.EtBadRequestException;
import com.placideh.tracker.exceptions.EtResourceNotFoundException;
import com.placideh.tracker.repositories.TransactionRepository;
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{
	@Autowired
	TransactionRepository transactionRepository;
	@Override
	public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		return transactionRepository.findAll(userId, categoryId);
		
	}

	@Override
	public Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		return transactionRepository.findById(userId, categoryId, transactionId);
	}

	@Override
	public Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note,
			Long transactionalDate) throws EtBadRequestException {
		int transactionId=transactionRepository.create(userId, categoryId, amount, note, transactionalDate);
		return transactionRepository.findById(userId, categoryId, categoryId);
	}

	@Override
	public void updateTransaction(Integer userId, Integer categoryId, Integer transactionId,Transaction transaction)
			throws EtBadRequestException {
		transactionRepository.update(userId, categoryId, transactionId, transaction);
	}

	@Override
	public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		transactionRepository.removeById(userId, categoryId, transactionId);
		
	}

}
