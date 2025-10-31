package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.models.BookItem;
import com.example.library.models.BookStatus;
import com.example.library.models.TransactionType;
import com.example.library.reposiories.BookItemRepository;
import com.example.library.reposiories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class ReturnService {
    private BookRepository bookRepository;
    private BookItemRepository bookItemRepository;

    public ReturnService(BookRepository bookRepository, BookItemRepository bookItemRepository){
        this.bookRepository=bookRepository;
        this.bookItemRepository= bookItemRepository;
    }

    public String returnBook(String barcode){
        BookItem bookItem = bookItemRepository.findByBarcode(barcode)
                .orElseThrow(()-> new RuntimeException("No book found with this barcode"));


        if (bookItem.getTransactionType() == TransactionType.BUY) {
            throw new RuntimeException("Bought books cannot be returned");
        }
        bookItemRepository.delete(bookItem);

        // return back the book and adding in stock
        Book book= bookItem.getBook();
        book.setQuantity(book.getQuantity() + 1);
        if(book.getQuantity()>0){
            book.setBookstatus(BookStatus.AVAILABLE);
        }
        bookRepository.save(book);

        return "you have returned the book safely";

    }
}
