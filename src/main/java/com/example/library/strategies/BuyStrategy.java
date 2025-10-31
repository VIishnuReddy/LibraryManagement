package com.example.library.strategies;

import com.example.library.factories.BookItemFactory;
import com.example.library.models.*;
import com.example.library.reposiories.BookItemRepository;
import com.example.library.reposiories.BookRepository;
import com.example.library.reposiories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BuyStrategy implements BookingStrategy{
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private BookItemRepository bookItemRepository;

    public BuyStrategy(UserRepository userRepository,
                           BookRepository bookRepository,
                           BookItemRepository bookItemRepository){
        this.userRepository=userRepository;
        this.bookRepository=bookRepository;
        this.bookItemRepository = bookItemRepository;
    }
   public TransactionType getTransactionType(){
        return TransactionType.BUY;
    }
    @Override
    public BookItem processTransaction(Long userId, Long bookId) {
        // checking if book and user is valid
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(" User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Ordered book is not found"));
        // checking books are in stock
        if (book.getBookstatus().equals(BookStatus.NOT_AVAILABLE)) {
            throw new RuntimeException("Ordered book is not available. Come back later");
        }

        // creating bookItem and setting transaction
        BookItem bookItem = BookItemFactory.create(book,user, TransactionType.BUY);
        bookItemRepository.save(bookItem);

        book.setQuantity(book.getQuantity() - 1);
        if (book.getQuantity() < 1) {
            book.setBookstatus(BookStatus.NOT_AVAILABLE);
        }
        bookRepository.save(book);
        return bookItem;
    }
}
