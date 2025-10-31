package com.example.library.strategies;

import com.example.library.models.BookItem;
import com.example.library.models.TransactionType;

public interface BookingStrategy {
    TransactionType getTransactionType();
    BookItem processTransaction(Long userId, Long bookId);
}
