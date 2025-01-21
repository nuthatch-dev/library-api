package ru.nuthatch.libraryapi.repository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ru.nuthatch.libraryapi.entity.Author;

import java.util.List;
import java.util.Optional;

@Named
@Slf4j
@RequestScoped
public class AuthorRepository {
    
    private static final String PERSISTENCE_UNIT_NAME = "library-persistent-unit";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Transactional
    public Optional<Author> create(Author entity) {
        entityManager.persist(entity);
        return Optional.of(entityManager.find(Author.class, entity.getId()));
    }

    public Optional<Author> findById(long id) {
        return Optional.of(entityManager.find(Author.class, id));
    }

    @Transactional
    public List<Author> findAll() {
        return entityManager.createNamedQuery("Author.findAll", Author.class).getResultList();
    }

    @Transactional
    public Optional<Author> update(Author entity) {
        Optional<Author> result = Optional.empty();
        Author updatedAuthor = entityManager.find(Author.class, entity.getId());
        if (updatedAuthor != null) {
            entityManager.merge(entity);
            result = Optional.of(updatedAuthor);
        }
        return result;
    }

    @Transactional
    public boolean deleteById(long id) {
        boolean isDeleted = false;

        Author deletedAuthor = entityManager.find(Author.class, id);
        if (deletedAuthor != null) {
            entityManager.remove(deletedAuthor);
            isDeleted = true;
        }
        return isDeleted;
    }
}
