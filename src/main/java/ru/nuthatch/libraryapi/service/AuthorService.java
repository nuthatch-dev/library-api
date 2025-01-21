package ru.nuthatch.libraryapi.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.nuthatch.libraryapi.entity.Author;
import ru.nuthatch.libraryapi.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Named
@RequestScoped
public class AuthorService {

    @Inject
    private AuthorRepository repository;

    public Optional<Author> create(Author author) {
        return repository.create(author);
    }

    public Optional<Author> findById(long id) {
        return repository.findById(id);
    }

    public List<Author> findAll() {
        return repository.findAll();
    }

    public Optional<Author> update(Author author) {
        return repository.update(author);
    }

    public boolean deleteById(long id) {
        return repository.deleteById(id);
    }
}
