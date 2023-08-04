package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.Book;
import com.daniel.geramed.entity.BookPrice;
import com.daniel.geramed.entity.Store;
import com.daniel.geramed.model.request.BookRequest;
import com.daniel.geramed.model.response.BookResponse;
import com.daniel.geramed.repository.BookRepository;
import com.daniel.geramed.service.BookPriceService;
import com.daniel.geramed.service.BookService;
import com.daniel.geramed.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public BookResponse create(BookRequest request) {
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

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .price(bookPrice.getPrice())
                .stock(bookPrice.getStock())
                .build();
    }

    @Override
    public List<BookResponse> createBulk(List<BookRequest> requests) {
        return requests.stream().map(this::create).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public BookResponse update(BookRequest request) {
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
                bookPriceService.update(bookPrice.get());
                return BookResponse.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .isbn(book.getIsbn())
                        .author(book.getAuthor())
                        .publisher(book.getPublisher())
                        .price(request.getPrice())
                        .stock(request.getStock())
                        .build();
            }
            bookPrice.get().setIsActive(false);
        }

        BookPrice newBookPrice = bookPriceService.create(BookPrice.builder()
                .price(request.getPrice())
                .stock(request.getStock())
                .book(book)
                .store(store)
                .isActive(true)
                .build());

        List<BookPrice> bookPrices = book.getBookPrices();
        bookPrices.add(newBookPrice);
        book.setBookPrices(bookPrices);
        bookRepository.save(book);

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not Found"));

    }

    @Override
    public BookResponse findBookById(String id) {
        Book book = findById(id);
        BookPrice bookPrice = book.getBookPrices().stream().filter(BookPrice::getIsActive).findFirst()
                .orElse(BookPrice.builder().price(null).stock(null).build());

        book.setViews(book.getViews() + 1);
        bookRepository.save(book);

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .price(bookPrice.getPrice())
                .stock(bookPrice.getStock())
                .build();
    }

    @Override
    public List<BookResponse> findBookByTitleAuthorIsbn(String title, String author, String isbn) {
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
        Sort sort = Sort.by(Sort.Direction.DESC, "views");
        List<Book> books = bookRepository.findAll(specification,sort);
        List<BookResponse> responses = new ArrayList<>();
        for (Book book : books) {
            Optional<BookPrice> bookPrice = book.getBookPrices().stream().filter(BookPrice::getIsActive).findFirst();
            if (bookPrice.isEmpty() || !book.isActive()) continue;
            responses.add(BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .isbn(book.getIsbn())
                    .author(book.getAuthor())
                    .publisher(book.getPublisher())
                    .price(bookPrice.get().getPrice())
                    .stock(bookPrice.get().getStock())
                    .build());
        }
        return responses;
    }

    @Override
    public void delete(String id) {
        Book book = findById(id);
        book.setActive(false);
        bookRepository.save(book);
    }
}
