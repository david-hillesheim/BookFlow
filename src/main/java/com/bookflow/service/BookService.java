package com.bookflow.service;

import com.bookflow.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/books")
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

}
