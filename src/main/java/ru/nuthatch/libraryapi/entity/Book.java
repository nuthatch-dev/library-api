package ru.nuthatch.libraryapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@NamedNativeQuery(name = "Book.findAll",
        query = "SELECT b.* FROM book b",
        resultClass = Book.class)

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "id_book", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_author", referencedColumnName = "id"))
    private Set<Author> authorSet = new HashSet<>();

    @Column(name = "date_of_publication")
    private Date dateOfPublication;

    @Column(name = "page_count")
    private int pageCount;

    private String description;

    @Column(name = "number_of_units")
    private int numberOfUnits;
}
