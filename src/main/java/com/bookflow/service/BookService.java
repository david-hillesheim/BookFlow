package com.bookflow.service;

import com.bookflow.model.Book;
import com.bookflow.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book registerBook(Book book) {
        return bookRepository.save(book);
    }

}
