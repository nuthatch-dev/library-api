package ru.nuthatch.libraryapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nuthatch.libraryapi.entity.Author;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuthorRepositoryTest {

    @InjectMocks
    private AuthorRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Author> typedQuery;

    @Test
    void testCreate() {
        Author author = new Author();
        author.setId(1L);

        when(entityManager.find(Author.class, author.getId())).thenReturn(author);

        Optional<Author> result = repository.create(author);

        assertTrue(result.isPresent());
        assertEquals(author, result.get());
        verify(entityManager, times(1)).persist(author);
    }

    @Test
    void testFindById() {
        long id = 1L;
        Author author = new Author();
        author.setId(id);

        when(entityManager.find(Author.class, id)).thenReturn(author);

        Optional<Author> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(author, result.get());
        verify(entityManager, times(1)).find(Author.class, id);
    }

    @Test
    void testFindAll() {
        List<Author> authors = List.of(new Author());

        when(entityManager.createNamedQuery("Author.findAll", Author.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(authors);

        List<Author> result = repository.findAll();

        assertEquals(authors.size(), result.size());
        verify(entityManager, times(1)).createNamedQuery("Author.findAll", Author.class);
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testUpdate() {
        Author author = new Author();
        author.setId(1L);

        when(entityManager.find(Author.class, author.getId())).thenReturn(author);

        Optional<Author> result = repository.update(author);

        assertTrue(result.isPresent());
        assertEquals(author, result.get());
        verify(entityManager, times(1)).merge(author);
    }

    @Test
    void testDeleteById() {
        long id = 1L;
        Author author = new Author();
        author.setId(id);

        when(entityManager.find(Author.class, id)).thenReturn(author);

        boolean result = repository.deleteById(id);

        assertTrue(result);
        verify(entityManager, times(1)).remove(author);
    }

}
