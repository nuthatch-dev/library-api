package ru.nuthatch.libraryapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nuthatch.libraryapi.entity.Reader;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ReaderRepositoryTest {

    @InjectMocks
    private ReaderRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Reader> typedQuery;

    @Test
    void testCreate() {
        Reader reader = new Reader();
        reader.setId(1L);

        when(entityManager.find(Reader.class, reader.getId())).thenReturn(reader);

        Optional<Reader> result = repository.create(reader);

        assertTrue(result.isPresent());
        assertEquals(reader, result.get());
        verify(entityManager, times(1)).persist(reader);
    }

    @Test
    void testFindById() {
        long id = 1L;
        Reader reader = new Reader();
        reader.setId(id);

        when(entityManager.find(Reader.class, id)).thenReturn(reader);

        Optional<Reader> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(reader, result.get());
        verify(entityManager, times(1)).find(Reader.class, id);
    }

    @Test
    void testFindAll() {
        List<Reader> readers = List.of(new Reader());

        when(entityManager.createNamedQuery("Reader.findAll", Reader.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(readers);

        List<Reader> result = repository.findAll();

        assertEquals(readers.size(), result.size());
        verify(entityManager, times(1)).createNamedQuery("Reader.findAll", Reader.class);
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testUpdate() {
        Reader reader = new Reader();
        reader.setId(1L);

        when(entityManager.find(Reader.class, reader.getId())).thenReturn(reader);

        Optional<Reader> result = repository.update(reader);

        assertTrue(result.isPresent());
        assertEquals(reader, result.get());
        verify(entityManager, times(1)).merge(reader);
    }

    @Test
    void testDeleteById() {
        long id = 1L;
        Reader reader = new Reader();
        reader.setId(id);

        when(entityManager.find(Reader.class, id)).thenReturn(reader);

        boolean result = repository.deleteById(id);

        assertTrue(result);
        verify(entityManager, times(1)).remove(reader);
    }

}
