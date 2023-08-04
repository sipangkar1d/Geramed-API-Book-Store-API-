package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.BookPrice;
import com.daniel.geramed.repository.BookPriceRepository;
import com.daniel.geramed.service.BookPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BookPriceServiceImpl implements BookPriceService {
    private final BookPriceRepository bookPriceRepository;

    @Override
    public BookPrice create(BookPrice bookPrice) {
        return bookPriceRepository.saveAndFlush(bookPrice);
    }

    @Override
    public BookPrice getById(String id) {
        return bookPriceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Price Not Found"));
    }
}
