package com.manage.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerDto {

    private Long id;

    @Size(min = 3, max = 100)
    private String username;

    @Size(min = 8)
    private String password;

    @Email
    private String email;

    private List<BookDto> books;

    private String roleName; // BORROWER, LIBRARIAN, ADMIN
}