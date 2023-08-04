package com.daniel.geramed.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_book")
public class Book extends BaseAuditor<String>{
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "book_id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "views")
    private Integer views;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "book")
    private List<BookPrice> bookPrices;
}
