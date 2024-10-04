package com.manage.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "borrowers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Borrower implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 3, max = 100)
    private String username;

    @Size(min = 8)
    private String password;

    @Email
    private String email;

    @OneToMany(mappedBy = "borrower")
    private List<Book> books;

    @Enumerated(EnumType.STRING)
    private Role role; // BORROWER, LIBRARIAN, ADMIN
}