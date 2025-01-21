package ru.nuthatch.libraryapi.repository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ru.nuthatch.libraryapi.entity.Issue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Named
@Slf4j
@RequestScoped
public class IssueRepository {

    private static final String PERSISTENCE_UNIT_NAME = "library-persistent-unit";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Transactional
    public Optional<Issue> create(Issue entity) {
        entityManager.persist(entity);
        return Optional.of(entityManager.find(Issue.class, entity.getId()));
    }

    @Transactional
    public Optional<Issue> findById(long id) {
        return Optional.of(entityManager.find(Issue.class, id));
    }

    @Transactional
    public List<Issue> findAll() {
        return entityManager.createNamedQuery("Issue.findAll", Issue.class).getResultList();
    }

    @Transactional
    public Optional<Issue> update(Issue entity) {
        Optional<Issue> result = Optional.empty();
        Issue updatedIssue = entityManager.find(Issue.class, entity.getId());
        if (updatedIssue != null) {
            entityManager.merge(entity);
            result = Optional.of(updatedIssue);
        }
        return result;
    }

    @Transactional
    public boolean deleteById(long id) {
        boolean isDeleted = false;

        Issue deletedIssue = entityManager.find(Issue.class, id);
        if (deletedIssue != null) {
            entityManager.remove(deletedIssue);
            isDeleted = true;
        }
        return isDeleted;
    }

    @Transactional
    public int setBookReturnedById(long issueId) {
        return entityManager.createNamedQuery("Issue.setBookReturnedById")
                .setParameter("returned_date", new Date())
                .setParameter("issue_id", issueId)
                .executeUpdate();
    }

    @Transactional
    public List<Issue> findAllIssuesByReaderForPeriod(long readerId, Date startDate, Date endDate) {
        return entityManager.createNamedQuery("Issue.findAllIssuesByReaderForPeriod", Issue.class)
                .setParameter("reader_id", readerId)
                .setParameter("start_date", startDate)
                .setParameter("end_date", endDate)
                .getResultList();
    }

    @Transactional
    public int findCountOfIssuesByReaderForPeriod(long readerId, Date startDate, Date endDate) {
        return entityManager.createNamedQuery("Issue.findCountOfIssuesByReaderForPeriod", Integer.class)
                .setParameter("reader_id", readerId)
                .setParameter("start_date", startDate)
                .setParameter("end_date", endDate)
                .getSingleResult();
    }

}
