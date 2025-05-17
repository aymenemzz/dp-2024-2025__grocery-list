package com.fges.adapters.in.web;

import com.fges.application.port.in.GroceryListService;
import com.fges.domain.GroceryItem;
import com.fges.adapters.in.web.mapper.WebGroceryItemDTOMapper;
import fr.anthonyquere.MyGroceryShop;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class GroceryShopAdapter implements MyGroceryShop {
    private final GroceryListService service;

    public GroceryShopAdapter(GroceryListService service) {
        this.service = service;
    }

    @Override
    public List<WebGroceryItem> getGroceries() {
        // convertis depuis GroceryItem vers WebGroceryItem via WebGroceryItemDTO
        return service.getAllItems().getGroceryItemList().stream()
                .map(WebGroceryItemDTOMapper::fromDomain)
                .map(WebGroceryItemDTOMapper::toWeb)
                .collect(Collectors.toList());
    }

    @Override
    public void addGroceryItem(String name, int quantity, String category) {
        WebGroceryItem webItem = new WebGroceryItem(name, quantity, category);
        GroceryItem item = WebGroceryItemDTOMapper.toDomain(
                WebGroceryItemDTOMapper.fromWeb(webItem)
        );
        service.addItem(item);
    }

    @Override
    public void removeGroceryItem(String name) {
        WebGroceryItem webItem = new WebGroceryItem(name, 0, "");
        GroceryItem item = WebGroceryItemDTOMapper.toDomain(
                WebGroceryItemDTOMapper.fromWeb(webItem)
        );
        service.deleteItem(item);
    }

    @Override
    public Runtime getRuntime() {
        return new Runtime(
                LocalDate.now(),
                System.getProperty("java.version"),
                System.getProperty("os.name")
        );
    }
}