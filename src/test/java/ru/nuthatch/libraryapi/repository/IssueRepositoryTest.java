package ru.nuthatch.libraryapi.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nuthatch.libraryapi.entity.Issue;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class IssueRepositoryTest {

    @InjectMocks
    private IssueRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Issue> typedQuery;

    @Test
    void testCreate() {
        Issue issue = new Issue();
        issue.setId(1L);

        when(entityManager.find(Issue.class, issue.getId())).thenReturn(issue);

        Optional<Issue> result = repository.create(issue);

        assertTrue(result.isPresent());
        assertEquals(issue, result.get());
        verify(entityManager, times(1)).persist(issue);
    }

    @Test
    void testFindById() {
        long id = 1L;
        Issue issue = new Issue();
        issue.setId(id);

        when(entityManager.find(Issue.class, id)).thenReturn(issue);

        Optional<Issue> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(issue, result.get());
        verify(entityManager, times(1)).find(Issue.class, id);
    }

    @Test
    void testFindAll() {
        List<Issue> issues = List.of(new Issue());

        when(entityManager.createNamedQuery("Issue.findAll", Issue.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(issues);

        List<Issue> result = repository.findAll();

        assertEquals(issues.size(), result.size());
        verify(entityManager, times(1)).createNamedQuery("Issue.findAll", Issue.class);
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testUpdate() {
        Issue issue = new Issue();
        issue.setId(1L);

        when(entityManager.find(Issue.class, issue.getId())).thenReturn(issue);

        Optional<Issue> result = repository.update(issue);

        assertTrue(result.isPresent());
        assertEquals(issue, result.get());
        verify(entityManager, times(1)).merge(issue);
    }

    @Test
    void testDeleteById() {
        long id = 1L;
        Issue issue = new Issue();
        issue.setId(id);

        when(entityManager.find(Issue.class, id)).thenReturn(issue);

        boolean result = repository.deleteById(id);

        assertTrue(result);
        verify(entityManager, times(1)).remove(issue);
    }

}
