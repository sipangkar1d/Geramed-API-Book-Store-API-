package com.daniel.geramed.model.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
public class BookRequest {
    private String id;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "isbn is required")
    private String isbn;
    @NotBlank(message = "author is required")
    private String author;
    @NotBlank(message = "publisher is required")
    private String publisher;
    @NotBlank(message = "publication date is required")
    private Date publicationDate;
    @NotNull(message = "price is required")
    private Long price;
    @NotNull(message = "stock is required")
    private Integer stock;
    @NotBlank(message = "storeId is required")
    private String storeId;
}
