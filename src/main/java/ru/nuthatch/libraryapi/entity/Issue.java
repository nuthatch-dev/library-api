package ru.nuthatch.libraryapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@NamedNativeQuery(name = "Issue.findAll", query = "SELECT i.* FROM issue i", resultClass = Issue.class)

@NamedNativeQuery(name = "Issue.setBookReturnedById",
        query = "UPDATE issue i SET returned_at = :returned_date WHERE id = :issue_id")

@NamedNativeQuery(name = "Issue.findAllIssuesByReaderForPeriod",
        query = "SELECT i.* FROM issue i " +
                "WHERE i.reader_id = :reader_id " +
                "AND i.issued_at BETWEEN :start_date AND :end_date",
        resultClass = Issue.class)

@NamedNativeQuery(name = "Issue.findCountOfIssuesByReaderForPeriod",
        query = "SELECT count(*) FROM issue i " +
                "WHERE i.reader_id = :reader_id " +
                "AND i.issued_at BETWEEN :start_date AND :end_date",
        resultClass = Integer.class)

@Getter
@Setter
@Entity
@Table(name = "issue")
public class Issue implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Reader reader;

    @Column(name = "issued_at", updatable = false)
    private Date issuedAt;

    @Column(name = "returned_at")
    private Date returnedAt;

    @PrePersist
    private void onCreate() {
        issuedAt = new Date();
    }

}
