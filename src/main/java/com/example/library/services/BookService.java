package com.example.library.services;

import com.example.library.models.*;
import com.example.library.reposiories.BookItemRepository;
import com.example.library.reposiories.BookRepository;
import com.example.library.strategies.BookingStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BookService {

    private final Map<TransactionType, BookingStrategy> strategyMap;
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;
    private ReturnService returnService;

    public BookService(List<BookingStrategy> strategies,
                       BookRepository bookRepository,
                       BookItemRepository bookItemRepository,
                        ReturnService returnService) {

        this.bookRepository = bookRepository;
        this.bookItemRepository = bookItemRepository;

        this.strategyMap = new HashMap<>();
        for (BookingStrategy strategy : strategies) {
            strategyMap.put(strategy.getTransactionType(), strategy);
        }
        this.returnService=returnService;
    }

    public BookItem handleTransaction(TransactionType type, Long userId, Long bookId) {
        BookingStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new RuntimeException("No strategy found for transaction type " + type);
        }
        return strategy.processTransaction(userId, bookId);
    }

    public String returnBook(String barcode) {
       return returnService.returnBook(barcode);
    }


}
