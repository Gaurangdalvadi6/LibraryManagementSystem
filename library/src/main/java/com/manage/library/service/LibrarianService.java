package com.manage.library.service;

import com.manage.library.dto.BookDto;
import com.manage.library.entity.Book;
import com.manage.library.entity.BookStatus;
import com.manage.library.entity.Borrower;
import com.manage.library.repository.BookRepository;
import com.manage.library.repository.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibrarianService {

    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;


    public List<BookDto> getBooks() {
        return bookRepository.findAll().stream()
                .map(book -> BookDto.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .build())
                .collect(Collectors.toList());
    }

    public String issueBook(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow();
        Borrower borrower = borrowerRepository.findById(book.getBorrower().getId()).orElseThrow();

        if (book.getStatus() == BookStatus.AVAILABLE) {
            book.setBorrower(borrower);
            book.setStatus(BookStatus.ISSUED);
            book.setStatus(BookStatus.ISSUED);
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-mm-yyyy");
            book.setIssuedDate(LocalDateTime.now().format(dt));
            book.setReturnDate(String.valueOf(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000))); // 2 weeks
            bookRepository.save(book);
            return "Book issued successfully";
        } else {
            return "Book is not available";
        }
    }

    public String returnBook(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow();
        if (book.getStatus() == BookStatus.ISSUED) {
            book.setBorrower(null);
            book.setStatus(BookStatus.AVAILABLE);
            book.setIssuedDate(null);
            book.setReturnDate(null);
            bookRepository.save(book);
            return "Book returned successfully";
        } else {
            return "Book is not issued";
        }
    }
}