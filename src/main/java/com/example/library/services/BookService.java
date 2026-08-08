package com.example.library.services;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.models.*;
import com.example.library.reposiories.BookItemRepository;
import com.example.library.reposiories.BookRepository;
import com.example.library.strategies.BookingStrategy;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class BookService {

    private final Map<TransactionType, BookingStrategy> strategyMap;
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;
    private TransactionService transactionService;
    private ReturnService returnService;

    public BookService(List<BookingStrategy> strategies,
                       BookRepository bookRepository,
                       BookItemRepository bookItemRepository,
                        ReturnService returnService,
                       TransactionService transactionService) {

        this.bookRepository = bookRepository;
        this.bookItemRepository = bookItemRepository;

        this.strategyMap = new HashMap<>();
        for (BookingStrategy strategy : strategies) {
            strategyMap.put(strategy.getTransactionType(), strategy);
        }
        this.returnService=returnService;
        this.transactionService=transactionService;
    }

    public BookItem handleTransaction(TransactionType type, Long userId, Long bookId) {
        BookingStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new RuntimeException("No strategy found for transaction type " + type);
        }
        BookItem bookItem = strategy.processTransaction(userId, bookId);
        transactionService.recordTransaction(bookItem.getBook().getName(),
                type.name(), bookItem.getUser(), bookItem.getBorrowDate());
        return bookItem;
    }

    public String returnBook(String barcode) {
       Optional<BookItem> bookItem= bookItemRepository.findByBarcode(barcode);
        transactionService.recordTransaction(bookItem.get().getBook().getName(),
                                                TransactionType.RETURN.name(), bookItem.get().getUser(),
                                                bookItem.get().getBorrowDate());
        return returnService.returnBook(barcode);
    }

    @Cacheable(value = "booksList")
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @CachePut(value = "books", key = "#result.id")
    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    @Cacheable(value = "books", key = "#id")
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(()->
                new BookNotFoundException("Please enter a valid book id:"+id));
    }

    @CachePut(value = "books", key = "#id")
    public Book updateBook(Long id, Book book){
        Book existingbook = bookRepository.findById(id).orElseThrow(()
                -> new BookNotFoundException("Book not found with id "+id));

        existingbook.setName(book.getName());
        existingbook.setPrice(book.getPrice());
        existingbook.setGenre(book.getGenre());
        existingbook.setBookstatus(book.getBookstatus());
        existingbook.setQuantity(book.getQuantity());

        return bookRepository.save(existingbook);
    }
}
