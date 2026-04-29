package com.bookflow.controller;

import com.bookflow.dto.request.BookRequest;
import com.bookflow.dto.response.BookResponse;
import com.bookflow.model.Book;
import com.bookflow.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookResponse registerBook(@RequestBody BookRequest bookRequest) {
        return bookService.registerBook(bookRequest);
    }

    @GetMapping
    public List<BookResponse> listAllBooks() {
        return bookService.listAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponse findBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }
}
