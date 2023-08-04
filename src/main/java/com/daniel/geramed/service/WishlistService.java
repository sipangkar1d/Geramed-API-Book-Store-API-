package com.daniel.geramed.service;

import com.daniel.geramed.entity.WishList;

public interface WishlistService {
    WishList create(WishList wishList);
    WishList findById(String id);
    void delete(String id);

}
