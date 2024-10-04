package com.manage.library.service;

import com.manage.library.dto.BookDto;
import com.manage.library.entity.Book;
import com.manage.library.entity.BookStatus;
import com.manage.library.entity.Borrower;
import com.manage.library.repository.BookRepository;
import com.manage.library.repository.BorrowerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowerService {

    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BookRepository bookRepository, BorrowerRepository borrowerRepository) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
    }

    public List<BookDto> getBorrowedBooks() {
        Borrower borrower = borrowerRepository.findByUsername(getCurrentUsername());
        return borrower.getBooks().stream()
                .map(book -> BookDto.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .build())
                .collect(Collectors.toList());
    }

    public String requestBook(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow();
        Borrower borrower = borrowerRepository.findByUsername(getCurrentUsername());

        if (book.getStatus() == BookStatus.AVAILABLE) {
            book.setBorrower(borrower);
            book.setStatus(BookStatus.ISSUED);
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-mm-yyyy");
            book.setIssuedDate(LocalDateTime.now().format(dt));
            book.setReturnDate(String.valueOf(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000))); // 2 weeks
            bookRepository.save(book);
            return "Book requested successfully";
        } else {
            return "Book is not available";
        }
    }

    public String returnBook(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow();
        Borrower borrower = borrowerRepository.findByUsername(getCurrentUsername());

        if (book.getBorrower().equals(borrower)) {
            book.setBorrower(null);
            book.setStatus(BookStatus.AVAILABLE);
            book.setIssuedDate(null);
            book.setReturnDate(null);
            bookRepository.save(book);
            return "Book returned successfully";
        } else {
            return "You did not borrow this book";
        }
    }

    private String getCurrentUsername() {
        // Assuming you're using Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}