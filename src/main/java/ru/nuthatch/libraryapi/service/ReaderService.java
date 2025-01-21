package ru.nuthatch.libraryapi.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.nuthatch.libraryapi.entity.Reader;
import ru.nuthatch.libraryapi.repository.ReaderRepository;

import java.util.List;
import java.util.Optional;

@Named
@RequestScoped
public class ReaderService {

    @Inject
    private ReaderRepository repository;

    public Optional<Reader> create(Reader reader) {
        return repository.create(reader);
    }

    public Optional<Reader> findById(long id) {
        return repository.findById(id);
    }

    public List<Reader> findAll() {
        return repository.findAll();
    }

    public Optional<Reader> update(Reader reader) {
        return repository.update(reader);
    }

    public boolean deleteById(long id) {
        return repository.deleteById(id);
    }

}
