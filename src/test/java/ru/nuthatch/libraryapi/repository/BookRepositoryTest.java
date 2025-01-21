package ru.nuthatch.libraryapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nuthatch.libraryapi.entity.Book;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BookRepositoryTest {

    @InjectMocks
    private BookRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Book> typedQuery;

    @Test
    void testCreate() {
        Book book = new Book();
        book.setId(1L);

        when(entityManager.find(Book.class, book.getId())).thenReturn(book);

        Optional<Book> result = repository.create(book);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        verify(entityManager, times(1)).persist(book);
    }

    @Test
    void testFindById() {
        long id = 1L;
        Book book = new Book();
        book.setId(id);

        when(entityManager.find(Book.class, id)).thenReturn(book);

        Optional<Book> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        verify(entityManager, times(1)).find(Book.class, id);
    }

    @Test
    void testFindAll() {
        List<Book> books = List.of(new Book());

        when(entityManager.createNamedQuery("Book.findAll", Book.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(books);

        List<Book> result = repository.findAll();

        assertEquals(books.size(), result.size());
        verify(entityManager, times(1)).createNamedQuery("Book.findAll", Book.class);
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testUpdate() {
        Book book = new Book();
        book.setId(1L);

        when(entityManager.find(Book.class, book.getId())).thenReturn(book);

        Optional<Book> result = repository.update(book);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        verify(entityManager, times(1)).merge(book);
    }

    @Test
    void testDeleteById() {
        long id = 1L;
        Book book = new Book();
        book.setId(id);

        when(entityManager.find(Book.class, id)).thenReturn(book);

        boolean result = repository.deleteById(id);

        assertTrue(result);
        verify(entityManager, times(1)).remove(book);
    }

}
