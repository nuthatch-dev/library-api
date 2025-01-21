package ru.nuthatch.libraryapi.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.nuthatch.libraryapi.entity.Book;
import ru.nuthatch.libraryapi.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Named
@RequestScoped
public class BookService {

    @Inject
    private BookRepository repository;

    public Optional<Book> create(Book book) {
        return repository.create(book);
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Optional<Book> update(Book book) {
        return repository.update(book);
    }

    public boolean deleteById(long id) {
        return repository.deleteById(id);
    }
    
}
