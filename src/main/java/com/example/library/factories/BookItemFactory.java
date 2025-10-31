package com.example.library.factories;

import com.example.library.models.Book;
import com.example.library.models.BookItem;
import com.example.library.models.TransactionType;
import com.example.library.models.User;

import java.time.LocalDate;

public class BookItemFactory {
    public static BookItem create(Book book, User user, TransactionType type) {
        BookItem item = new BookItem();
        item.setBook(book);
        item.setUser(user);
        item.setBorrowDate(LocalDate.now());
        item.setTransactionType(type);
        return item;
    }
}
