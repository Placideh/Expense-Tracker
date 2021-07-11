package com.placideh.tracker.services;

import java.util.List;

import com.placideh.tracker.domain.Transaction;
import com.placideh.tracker.exceptions.EtBadRequestException;
import com.placideh.tracker.exceptions.EtResourceNotFoundException;

public interface TransactionService {
	List<Transaction> fetchAllTransactions(Integer userId,Integer categoryId);
	
	Transaction fetchTransactionById(Integer userId,Integer categoryId,Integer transactionId) throws EtResourceNotFoundException;
	
	Transaction addTransaction (Integer userId,Integer categoryId,Double amount ,String note ,Long transactionalDate) throws EtBadRequestException;
	
	void updateTransaction(Integer userId, Integer categoryId,Integer transactionId) throws EtBadRequestException;
	
	
	void removeTransaction(Integer userId,Integer categoryId,Integer transactionId)throws EtResourceNotFoundException;
}
