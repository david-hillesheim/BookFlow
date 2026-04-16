package com.bookflow.service;

import com.bookflow.model.Book;
import com.bookflow.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book registerBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado para o id: " + id));
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado para o id: " + id));

        if (bookDetails.getTitle() != null) {
            existingBook.setTitle(bookDetails.getTitle());
        }

        if (bookDetails.getAuthor() != null) {
            existingBook.setAuthor(bookDetails.getAuthor());
        }

        if (bookDetails.getTotalCopies() != null) {
            existingBook.setTotalCopies(bookDetails.getTotalCopies());
        }

        return bookRepository.save(existingBook);
    }

    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)){
            throw new RuntimeException("Não foi possível encontrar livro para o id: " + id);
        }
        bookRepository.deleteById(id);
    }

}
