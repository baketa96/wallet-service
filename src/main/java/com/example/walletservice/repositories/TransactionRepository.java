package com.example.walletservice.repositories;

import com.example.walletservice.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByPlayerAccountId(Long playerId);

}
