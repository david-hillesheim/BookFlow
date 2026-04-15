package com.bookflow.controller;

import com.bookflow.model.Book;
import com.bookflow.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    public Book registerBook(@RequestBody Book book) {
        return bookService.registerBook(book);
    }
}
