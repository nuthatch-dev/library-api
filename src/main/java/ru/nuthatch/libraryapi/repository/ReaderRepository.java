package ru.nuthatch.libraryapi.repository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ru.nuthatch.libraryapi.entity.Reader;

import java.util.List;
import java.util.Optional;

@Named
@Slf4j
@RequestScoped
public class ReaderRepository {

    private static final String PERSISTENCE_UNIT_NAME = "library-persistent-unit";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Transactional
    public Optional<Reader> create(Reader entity) {
        entityManager.persist(entity);
        return Optional.of(entityManager.find(Reader.class, entity.getId()));
    }

    public Optional<Reader> findById(long id) {
        return Optional.of(entityManager.find(Reader.class, id));
    }

    public List<Reader> findAll() {
        Query readerQuery = entityManager.createNativeQuery("SELECT r.* FROM reader AS r", Reader.class);
        return readerQuery.getResultList();
    }

    @Transactional
    public Optional<Reader> update(Reader entity) {
        Optional<Reader> result = Optional.empty();
        Reader updatedReader = entityManager.find(Reader.class, entity.getId());
        if (updatedReader != null) {
            entityManager.refresh(entity);
            result = Optional.of(updatedReader);
        }
        return result;
    }

    @Transactional
    public boolean deleteById(long id) {
        boolean isDeleted = false;

        Reader deletedReader = entityManager.find(Reader.class, id);
        if (deletedReader != null) {
            entityManager.remove(deletedReader);
            isDeleted = true;
        }
        return isDeleted;
    }
}
