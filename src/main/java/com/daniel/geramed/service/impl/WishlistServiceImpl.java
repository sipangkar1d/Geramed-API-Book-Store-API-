package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.WishList;
import com.daniel.geramed.repository.WishlistRepository;
import com.daniel.geramed.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;

    @Override
    public WishList create(WishList wishList) {
        return wishlistRepository.saveAndFlush(wishList);
    }

    @Override
    public WishList findById(String id) {
        return wishlistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist not found"));
    }

    @Override
    public void delete(String id) {
        WishList wishlist = findById(id);
        wishlistRepository.delete(wishlist);
    }
}
