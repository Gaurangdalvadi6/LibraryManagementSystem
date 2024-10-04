package com.manage.library.service;

import com.manage.library.dto.BookDto;
import com.manage.library.entity.Book;
import com.manage.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final BookRepository bookRepository;

    public AdminService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String addBook(BookDto bookDto) {
        Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .build();
        bookRepository.save(book);
        return "Book added successfully";
    }

    public List<BookDto> getBooks() {
        return bookRepository.findAll().stream()
                .map(book -> BookDto.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .build())
                .collect(Collectors.toList());
    }

    public String updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        bookRepository.save(book);
        return "Book updated successfully";
    }

    public String deleteBook(Long id) {
        bookRepository.deleteById(id);
        return "Book deleted successfully";
    }
}