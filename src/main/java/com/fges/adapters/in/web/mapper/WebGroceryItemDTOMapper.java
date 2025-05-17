package com.fges.adapters.in.web.mapper;

import com.fges.adapters.in.web.dto.WebGroceryItemDTO;
import fr.anthonyquere.MyGroceryShop.WebGroceryItem;
import com.fges.domain.GroceryItem;

public class WebGroceryItemDTOMapper {
    public static WebGroceryItem toWeb(WebGroceryItemDTO dto) {
        return new WebGroceryItem(dto.getName(), dto.getQuantity(), dto.getCategory());
    }

    public static WebGroceryItemDTO fromWeb(WebGroceryItem web) {
        return new WebGroceryItemDTO(web.name(), web.quantity(), web.category());
    }

    public static GroceryItem toDomain(WebGroceryItemDTO dto) {
        return new GroceryItem(dto.getName(), dto.getQuantity(), dto.getCategory());
    }

    public static WebGroceryItemDTO fromDomain(GroceryItem item) {
        return new WebGroceryItemDTO(item.getName(), item.getQuantity(), item.getCategory());
    }
}