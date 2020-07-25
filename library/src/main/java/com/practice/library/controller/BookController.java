package com.practice.library.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.library.exception.ResourceNotFoundException;
import com.practice.library.model.Book;
import com.practice.library.repository.BookRepository;

@RestController
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@GetMapping("/books")
	public List<Book> getAllBooks() {
	    return bookRepository.findAll();
	}
	
	@PostMapping("/books")
	public Book createBook(@Valid @RequestBody Book book) {
	    return bookRepository.save(book);
	}
	
	@GetMapping("/book/{id}")
	public Book getBookById(@PathVariable(value = "id") Long bookId) {
	    return bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "isbn", bookId));
	}
	
	@PutMapping("/book/{id}")
	public Book updateBook(@PathVariable(value = "id") Long bookId,
	                                        @Valid @RequestBody Book bookDetails) {

		Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "isbn", bookId));
	    
		book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());

        Book updatedBook = bookRepository.save(book);
	    return updatedBook;
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long bookId) {
	    Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "isbn", bookId));

	    bookRepository.delete(book);
	    return ResponseEntity.ok().build();
	}
	
}
