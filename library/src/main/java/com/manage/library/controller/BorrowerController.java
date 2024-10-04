package com.manage.library.controller;

import com.manage.library.dto.BookDto;
import com.manage.library.service.BorrowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrower")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping("/get-borrowed-books")
    public ResponseEntity<List<BookDto>> getBorrowedBooks() {
        return ResponseEntity.ok(borrowerService.getBorrowedBooks());
    }

    @PostMapping("/request-book")
    public ResponseEntity<String> requestBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(borrowerService.requestBook(bookDto));
    }

    @PostMapping("/return-book")
    public ResponseEntity<String> returnBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(borrowerService.returnBook(bookDto));
    }
}