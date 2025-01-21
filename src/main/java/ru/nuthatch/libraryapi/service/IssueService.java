package ru.nuthatch.libraryapi.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.nuthatch.libraryapi.entity.Issue;
import ru.nuthatch.libraryapi.repository.IssueRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Named
@RequestScoped
public class IssueService {

    @Inject
    private IssueRepository repository;

    public Optional<Issue> create(Issue issue) {
        return repository.create(issue);
    }

    public Optional<Issue> findById(long id) {
        return repository.findById(id);
    }

    public List<Issue> findAll() {
        return repository.findAll();
    }

    public Optional<Issue> update(Issue issue) {
        return repository.update(issue);
    }

    public boolean deleteById(long id) {
        return repository.deleteById(id);
    }

    public int setBookReturnedById(long issueId) {
        return repository.setBookReturnedById(issueId);
    }

    public List<Issue> findAllIssuesByReaderForPeriod(long readerId, Date startDate, Date endDate) {
        return repository.findAllIssuesByReaderForPeriod(readerId, startDate, endDate);
    }

    public int findCountOfIssuesByReaderForPeriod(long readerId, Date startDate, Date endDate) {
        return repository.findCountOfIssuesByReaderForPeriod(readerId, startDate, endDate);
    }
}
