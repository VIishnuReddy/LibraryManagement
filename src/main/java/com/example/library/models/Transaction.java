package com.example.library.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookName;
    private String type;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Transaction(String bookName, String type, User user, LocalDate date) {
        this.bookName = bookName;
        this.type = type;
        this.user = user;
        this.date=date;
    }
}
