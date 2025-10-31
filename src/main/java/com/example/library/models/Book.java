package com.example.library.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String Genre;
    private Double price;
    @Enumerated(EnumType.STRING)
    private BookStatus bookstatus;
    private int quantity;

}
