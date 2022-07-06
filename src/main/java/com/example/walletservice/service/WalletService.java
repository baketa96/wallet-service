package com.example.walletservice.service;


import com.example.walletservice.ServiceException;
import com.example.walletservice.TransactionType;
import com.example.walletservice.models.PlayerAccount;
import com.example.walletservice.models.Transaction;
import com.example.walletservice.repositories.PlayerAccountRepository;
import com.example.walletservice.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private PlayerAccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private final List<Integer> transactionTypes = Arrays.asList(TransactionType.DEBIT.getValue(), TransactionType.CREDIT.getValue());

    public Double getPlayerBalance(Long playerId) throws ServiceException {
        return getPlayer(playerId).getBalance();

    }

    public PlayerAccount createPlayerAccount(PlayerAccount playerAccount) {
        return accountRepository.save(playerAccount);
    }

    public Transaction processTransaction(Long playerId, Transaction transaction) throws ServiceException{
        PlayerAccount account = getPlayer(playerId);
        validateTransaction(transaction, account);

        if(transaction.getTransactionType() == TransactionType.DEBIT.getValue()){
            if (account.getBalance() < transaction.getAmount())
                throw new ServiceException("Not enough funds on player account",HttpStatus.BAD_REQUEST.value());
            account.setBalance(account.getBalance() - transaction.getAmount());

        }else{
            account.setBalance(account.getBalance() + transaction.getAmount());
        }
        accountRepository.save(account);
        transactionRepository.save(transaction);
        return transaction;
    }

    private void validateTransaction(Transaction transaction, PlayerAccount account) throws ServiceException{

        if(transaction.getId() == null) throw new ServiceException("Transaction ID is not valid!", HttpStatus.BAD_REQUEST.value());

        Transaction transactionDB = transactionRepository.findById(transaction.getId()).orElse(null);

        if (transactionDB != null)  throw new ServiceException("Transaction with given ID already exist!", HttpStatus.BAD_REQUEST.value());

        if (transaction.getPlayerAccount() != null && !transaction.getPlayerAccount().getId().equals(account.getId()))
            throw new ServiceException("Player account on transaction is not ok!", HttpStatus.BAD_REQUEST.value());

        transaction.setPlayerAccount(account);

        if (transaction.getAmount() <= 0) throw new ServiceException("Transaction amount must be greater than 0", HttpStatus.BAD_REQUEST.value());

        if (transaction.getDate().after(new Date())) throw new ServiceException("Transaction date must be before or equal current date", HttpStatus.BAD_REQUEST.value());

        if (!transactionTypes.contains(transaction.getTransactionType())) throw new ServiceException("Transaction type is not valid!", HttpStatus.BAD_REQUEST.value());
    }



    public List<Transaction> getTransactionsByPlayerId(Long playerId) throws ServiceException {
        PlayerAccount playerAccount = getPlayer(playerId);
        return transactionRepository.findByPlayerAccountId(playerAccount.getId());
    }



    private PlayerAccount getPlayer(Long playerId) throws ServiceException{
        PlayerAccount playerAccount = accountRepository.findById(playerId).orElse(null);
        if (playerAccount == null)
            throw new ServiceException("Player doesn't exist!", HttpStatus.BAD_REQUEST.value());
        return playerAccount;
    }

}
