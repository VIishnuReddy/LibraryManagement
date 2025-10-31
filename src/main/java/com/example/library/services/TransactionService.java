package com.example.library.services;

import com.example.library.exceptions.UserNotFoundException;
import com.example.library.models.Transaction;
import com.example.library.models.User;
import com.example.library.reposiories.TransactionRepository;
import com.example.library.reposiories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository=transactionRepository;
    }

    public List<Transaction> getTransactionList(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return transactionRepository.findByUser(user);
    }

    public void recordTransaction(String bookName, String type, User user, LocalDate date){
        Transaction transaction = new Transaction(bookName, type, user, date);
        transactionRepository.save(transaction);
    }
}
