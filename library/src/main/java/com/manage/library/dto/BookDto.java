package com.manage.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;

    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3, max = 100)
    private String author;

    private String isbn;

    private Date publicationDate;

    private String status; // AVAILABLE, ISSUED, LOST, DAMAGED

    private String borrowerUsername;

    private String issuedDate;

    private String returnDate;
}