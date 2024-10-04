package com.manage.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3, max = 100)
    private String author;

    @Column(unique = true)
    private String isbn;

    private Date publicationDate;

    @Enumerated(EnumType.STRING)
    private BookStatus status; // AVAILABLE, ISSUED, LOST, DAMAGED

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

    private String issuedDate;

    private String returnDate;
}