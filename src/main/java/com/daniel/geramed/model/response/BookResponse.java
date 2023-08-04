package com.daniel.geramed.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class BookResponse {
    private String id;
    private String title;
    private String isbn;
    private String author;
    private String publisher;
    private Long price;
    private Integer stock;
}
