package com.manage.library.mapper;

import com.manage.library.dto.BookDto;
import com.manage.library.entity.Book;
import com.manage.library.entity.BookStatus;
import com.manage.library.entity.Borrower;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookMapper {

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationDate(book.getPublicationDate())
                .status(String.valueOf(book.getStatus()))
                .borrowerUsername(book.getBorrower() != null ? book.getBorrower().getUsername() : null)
                .issuedDate(book.getIssuedDate())
                .returnDate(book.getReturnDate())
                .build();
    }

    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setPublicationDate(bookDto.getPublicationDate());
        book.setStatus(BookStatus.valueOf(bookDto.getStatus()));
        if (bookDto.getBorrowerUsername() != null) {
            Borrower borrower = new Borrower();
            borrower.setUsername(bookDto.getBorrowerUsername());
            book.setBorrower(borrower);
        }
        book.setIssuedDate(bookDto.getIssuedDate());
        book.setReturnDate(bookDto.getReturnDate());
        return book;
    }

    public List<BookDto> toDtoList(List<Book> books) {
        return books.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Book> toEntityList(List<BookDto> bookDtos) {
        return bookDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}