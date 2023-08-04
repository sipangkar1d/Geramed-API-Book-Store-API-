package com.daniel.geramed.service;

import com.daniel.geramed.entity.BookPrice;

public interface BookPriceService {
    BookPrice create(BookPrice bookPrice);
    BookPrice getById(String id);

    BookPrice update(BookPrice bookPrice);

}
