package com.daniel.geramed.service;

import com.daniel.geramed.model.request.WishlistRequest;
import com.daniel.geramed.model.response.WishlistResponse;

import java.util.List;

public interface WishlistService {
    WishlistResponse create(WishlistRequest wishList);
    List<WishlistResponse> findAll();
    void delete(String id);

}
