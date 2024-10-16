package com.manage.library.service;

import com.manage.library.dto.BookDto;
import com.manage.library.entity.Book;
import com.manage.library.entity.BookStatus;
import com.manage.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .isbn(bookDto.getIsbn())
                .publicationDate(bookDto.getPublicationDate())
                .status(BookStatus.AVAILABLE)
                .borrower(null)
                .issuedDate(null)
                .returnDate(null)
                .build();
        bookRepository.save(book);
        return "Book added successfully";
    }

    public List<BookDto> getBooks() {
        return bookRepository.findAll().stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .isbn(book.getIsbn())
                        .status(book.getStatus().name())
                        .publicationDate(book.getPublicationDate())
                        .borrowerId(book.getBorrower() != null && book.getBorrower().getId() != null ? book.getBorrower().getId() : null)
                        .issuedDate(book.getIssuedDate())
                        .returnDate(book.getReturnDate())
                        .build())
                .toList();
    }

    public String updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        bookRepository.save(book);
        return "Book updated successfully";
    }

    public String deleteBook(Long id) {
        bookRepository.deleteById(id);
        return "Book deleted successfully";
    }
}