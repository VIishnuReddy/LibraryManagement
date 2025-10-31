package com.example.library.reposiories;

import com.example.library.models.Book;
import com.example.library.models.BookItem;
import com.example.library.models.TransactionType;
import com.example.library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookItemRepository extends JpaRepository<BookItem, Long> {
    Optional<BookItem> findByUserAndBook(User user, Book book);
    Optional<BookItem> findByBarcode(String barcode);
}
