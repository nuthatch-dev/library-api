package ru.nuthatch.libraryapi.repository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ru.nuthatch.libraryapi.entity.Author;
import ru.nuthatch.libraryapi.entity.Book;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Named
@Slf4j
@RequestScoped
public class BookRepository {

    private static final String PERSISTENCE_UNIT_NAME = "library-persistent-unit";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Transactional
    public Optional<Book> create(Book entity) {
        Set<Author> authors = entity.getAuthorSet();
        Set<Author> managedAuthors = new HashSet<>();
        for (Author author : authors) {
            Author existdAuthor = entityManager.find(Author.class, author.getId());
            if (existdAuthor != null) {
                managedAuthors.add(existdAuthor);
            }
        }
        entity.setAuthorSet(managedAuthors);
        entityManager.persist(entity);
        return Optional.of(entityManager.find(Book.class, entity.getId()));
    }

    public Optional<Book> findById(long id) {
        return Optional.of(entityManager.find(Book.class, id));
    }

    @Transactional
    public List<Book> findAll() {
        return entityManager.createNamedQuery("Book.findAll", Book.class).getResultList();
    }

    @Transactional
    public Optional<Book> update(Book entity) {
        Optional<Book> result = Optional.empty();
        Book updatedBook = entityManager.find(Book.class, entity.getId());
        if (updatedBook != null) {
            entityManager.merge(entity);
            result = Optional.of(updatedBook);
        }
        return result;
    }

    @Transactional
    public boolean deleteById(long id) {
        boolean isDeleted = false;

        Book deletedBook = entityManager.find(Book.class, id);
        if (deletedBook != null) {
            entityManager.remove(deletedBook);
            isDeleted = true;
        }
        return isDeleted;
    }
}
