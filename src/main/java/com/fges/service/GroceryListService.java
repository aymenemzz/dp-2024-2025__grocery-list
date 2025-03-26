package com.fges.service;

import com.fges.valueobject.Item;

import java.util.List;

public interface GroceryListService {

    void addItem(Item item);
    void deleteItem(Item item);
    List<Item> getAllItems();
}
