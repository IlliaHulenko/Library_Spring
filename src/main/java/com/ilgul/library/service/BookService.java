package com.ilgul.library.service;

import com.ilgul.library.dto.BookDto;
import com.ilgul.library.exception.NotFoundException;
import com.ilgul.library.entity.Book;
import com.ilgul.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    //This method return concrete book
    public Book get(Long id){
        Optional<Book> optional = bookRepository.findById(id);

        if(optional.isPresent()){
            return optional.get();
        }
        throw new NotFoundException("Book not found");
    }

    public Page<Book> getAll(String query, Pageable pageable){

        if(query != null){
            return bookRepository.findByQuery("%" + query.toLowerCase() + "%", pageable);
        }

        Page<Book> page = bookRepository.findAll(pageable);
        return page;
    }

    public Book create(BookDto dto){
        Book book = new Book(dto);
        Book saved = bookRepository.save(book);
        return saved;
    }

    public Book update(Long id, BookDto dto){
        Book original = get(id);
        original.setName(dto.getName());
        original.setAuthor(dto.getAuthor());
        original.setDescription(dto.getDescription());
        original.setPublisher(dto.getPublisher());
        original.setIsbn(dto.getIsbn());
        original.setYear(dto.getYear());

        Book updated = bookRepository.save(original);
        return updated;
    }

    public void delete(Long id){
        Book book = get(id);
        bookRepository.delete(book);
    }
}
