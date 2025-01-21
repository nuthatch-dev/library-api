package ru.nuthatch.libraryapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NamedNativeQuery(name = "Author.findAll",
        query = "SELECT a.* FROM author a",
        resultClass = Author.class)

@Getter
@Setter
@Entity
@Table(name = "author")
public class Author implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    private String email;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "authorSet")
    @JsonIgnore
    private Set<Book> bookSet = new HashSet<>();
}
