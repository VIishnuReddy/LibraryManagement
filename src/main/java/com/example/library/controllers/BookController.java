package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    BookService bookService;

    BookController(BookService bookService){
        this.bookService=bookService;
    }

    @GetMapping("/getBooks")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/getBooks/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }
    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book book){
        return ResponseEntity.ok(bookService.updateBook(id,book));
    }

}
