package com.manage.library.controller;

import com.manage.library.dto.BookDto;
import com.manage.library.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add-book")
    public ResponseEntity<String> addBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(adminService.addBook(bookDto));
    }

    @GetMapping("/get-books")
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.ok(adminService.getBooks());
    }

    @PutMapping("/update-book/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(adminService.updateBook(id, bookDto));
    }

    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteBook(id));
    }
}