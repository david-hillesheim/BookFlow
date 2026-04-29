package com.bookflow.service;

import com.bookflow.dto.request.BookRequest;
import com.bookflow.dto.response.BookResponse;
import com.bookflow.exception.ResourceNotFoundException;
import com.bookflow.model.Book;
import com.bookflow.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookResponse registerBook(BookRequest bookRequest) {
        Book book = new Book();

        book.setTitle(bookRequest.title());
        book.setAuthor(bookRequest.author());
        book.setIsbn(bookRequest.isbn());
        book.setTotalCopies(bookRequest.totalCopies());
        book.setAvailableCopies(book.getTotalCopies());

        Book savedBook = bookRepository.save(book);

        return new BookResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getIsbn(),
                savedBook.getTotalCopies(),
                savedBook.getAvailableCopies()
        );
    }

    public List<BookResponse> listAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getTotalCopies(),
                        book.getAvailableCopies()
                ))
                .toList();
    }

    public BookResponse findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para o id: " + id));
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getTotalCopies(),
                book.getAvailableCopies()
        );
    }

    public BookResponse updateBook(Long id, BookRequest bookDetails) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para o id: " + id));

        if (bookDetails.title() != null) {
            existingBook.setTitle(bookDetails.title());
        }

        if (bookDetails.author() != null) {
            existingBook.setAuthor(bookDetails.author());
        }

        if (bookDetails.totalCopies() != null) {
            existingBook.setTotalCopies(bookDetails.totalCopies());
        }

        Book updatedBook = bookRepository.save(existingBook);

        return new BookResponse(
                updatedBook.getId(),
                updatedBook.getTitle(),
                updatedBook.getAuthor(),
                updatedBook.getIsbn(),
                updatedBook.getTotalCopies(),
                updatedBook.getAvailableCopies()
        );
    }

    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)){
            throw new ResourceNotFoundException("Não foi possível encontrar livro para o id: " + id);
        }
        bookRepository.deleteById(id);
    }

}
