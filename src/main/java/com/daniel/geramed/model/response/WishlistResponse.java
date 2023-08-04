package com.daniel.geramed.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class WishlistResponse {
    private String id;
    private String book;
}
