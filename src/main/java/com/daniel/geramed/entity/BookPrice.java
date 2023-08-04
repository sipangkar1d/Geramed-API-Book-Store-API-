package com.daniel.geramed.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_book_price")
public class BookPrice {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "book_price_id")
    private String id;

    @Column(name = "price")
    private Long price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne()
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    private Book book;
}
