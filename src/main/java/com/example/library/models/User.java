package com.example.library.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String MobileNumber;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<BookItem> transactions;
}
