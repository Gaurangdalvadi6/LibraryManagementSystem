package com.manage.library.controller;

import com.manage.library.dto.BookDto;
import com.manage.library.service.LibrarianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/librarian")
public class LibrarianController {

    private final LibrarianService librarianService;

    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @GetMapping("/get-books")
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.ok(librarianService.getBooks());
    }

    @PostMapping("/issue-book")
    public ResponseEntity<String> issueBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(librarianService.issueBook(bookDto));
    }

    @PostMapping("/return-book")
    public ResponseEntity<String> returnBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(librarianService.returnBook(bookDto));
    }
}