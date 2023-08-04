package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.Book;
import com.daniel.geramed.entity.Customer;
import com.daniel.geramed.entity.WishList;
import com.daniel.geramed.model.request.WishlistRequest;
import com.daniel.geramed.model.response.WishlistResponse;
import com.daniel.geramed.repository.WishlistRepository;
import com.daniel.geramed.service.BookService;
import com.daniel.geramed.service.CustomerService;
import com.daniel.geramed.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final CustomerService customerService;
    private final BookService bookService;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public WishlistResponse create(WishlistRequest request) {
        Customer customer = customerService.findById(request.getCustomerId());
        Book book = bookService.findById(request.getBookId());
        WishList wishList = wishlistRepository.saveAndFlush(WishList.builder()
                .book(book)
                .customer(customer)
                .build());
        return WishlistResponse.builder()
                .id(wishList.getId())
                .book(book.getTitle())
                .build();
    }

    @Override
    public List<WishlistResponse> findAll() {
        return wishlistRepository.findAll().stream().map(wishList ->
                WishlistResponse.builder()
                        .id(wishList.getId())
                        .book(wishList.getBook().getTitle())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        WishList wishList = wishlistRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist Not Found"));
        wishlistRepository.delete(wishList);
    }
}
