package com.example.library.controllers;

import com.example.library.models.BookItem;
import com.example.library.models.BookStatus;
import com.example.library.models.TransactionType;
import com.example.library.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookItem")
public class BookItemController {

    private BookService bookService;

    public BookItemController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{type}/{userId}/{bookId}")
    public ResponseEntity<BookItem> handleTransaction(
            @PathVariable TransactionType type,
            @PathVariable Long userId,
            @PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.handleTransaction(type, userId, bookId));
    }

    @PostMapping("/return/{barcode}")
    public String returnBook(@PathVariable String barcode) {
        return bookService.returnBook(barcode);
    }


}