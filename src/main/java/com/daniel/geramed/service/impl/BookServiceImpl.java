package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.Book;
import com.daniel.geramed.entity.BookPrice;
import com.daniel.geramed.entity.Store;
import com.daniel.geramed.model.request.BookRequest;
import com.daniel.geramed.repository.BookRepository;
import com.daniel.geramed.service.BookPriceService;
import com.daniel.geramed.service.BookService;
import com.daniel.geramed.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final StoreService storeService;
    private final BookPriceService bookPriceService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Book create(BookRequest request) {
        Book book = bookRepository.saveAndFlush(
                Book.builder()
                        .title(request.getTitle())
                        .isbn(request.getIsbn())
                        .author(request.getAuthor())
                        .publisher(request.getPublisher())
                        .publicationDate(request.getPublicationDate())
                        .views(0)
                        .isActive(true)
                        .build());

        Store store = storeService.findById(request.getStoreId());

        BookPrice bookPrice = bookPriceService.create(
                BookPrice.builder()
                        .price(request.getPrice())
                        .stock(request.getStock())
                        .store(store)
                        .book(book)
                        .isActive(true)
                        .build()
        );

        book.setBookPrices(List.of(bookPrice));

        return book;
    }

    @Override
    public List<Book> createBulk(List<BookRequest> requests) {
        return requests.stream().map(this::create).collect(Collectors.toList());
    }

    @Override @Transactional(rollbackOn = Exception.class)
    public Book update(BookRequest request) {
        Book book = findById(request.getId());
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setAuthor(request.getAuthor());
        book.setPublisher(request.getPublisher());
        book.setPublicationDate(request.getPublicationDate());

        Store store = storeService.findById(request.getStoreId());

        Optional<BookPrice> bookPrice = book.getBookPrices().stream().filter(BookPrice::getIsActive).findFirst();
        if (bookPrice.isPresent()) {
            if (bookPrice.get().getPrice().equals(request.getPrice())) {
                bookPrice.get().setStock(request.getStock());
                return book;
            }
            bookPrice.get().setIsActive(false);
        }

        BookPrice newBookPrice = BookPrice.builder()
                .price(request.getPrice())
                .stock(request.getStock())
                .book(book)
                .store(store)
                .isActive(true)
                .build();

        List<BookPrice> bookPrices = book.getBookPrices();
        bookPrices.add(newBookPrice);
        book.setBookPrices(bookPrices);

        return book;
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public List<Book> findBookByTitleAuthorIsbn(String title, String author, String isbn) {
        Specification<Book> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (title != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (author != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
            }
            if (isbn != null) {
                predicates.add(criteriaBuilder.equal(root.get("isbn"), isbn));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        return bookRepository.findAll(specification);
    }

    @Override
    public void delete(String id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }
}
