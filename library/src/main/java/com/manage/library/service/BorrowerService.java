package com.manage.library.service;

import com.manage.library.dto.BookDto;
import com.manage.library.entity.Book;
import com.manage.library.entity.BookStatus;
import com.manage.library.entity.Borrower;
import com.manage.library.entity.User;
import com.manage.library.repository.BookRepository;
import com.manage.library.repository.BorrowerRepository;
import com.manage.library.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowerService {

    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final UserRepository userRepository;

    public BorrowerService(BookRepository bookRepository, BorrowerRepository borrowerRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
        this.userRepository = userRepository;
    }

    public List<BookDto> getBorrowedBooks() {
        Borrower borrower = borrowerRepository.findByEmail(getCurrentUsername());
        if (borrower == null || borrower.getBooks() == null || borrower.getBooks().isEmpty()) {
            return Collections.emptyList();
        }
        return borrower.getBooks().stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .issuedDate(book.getIssuedDate())
                        .returnDate(book.getReturnDate())
                        .build())
                .collect(Collectors.toList());
    }

    public String requestBook(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow();
        User user = userRepository.findByEmail(getCurrentUsername()).get();
        if (book.getStatus() == BookStatus.AVAILABLE) {
            book.setBorrower(new Borrower(user.getId()));
            book.setStatus(BookStatus.PENDING);
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
        Borrower borrower = borrowerRepository.findByEmail(getCurrentUsername());

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