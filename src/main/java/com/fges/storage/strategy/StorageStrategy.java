package com.fges.storage.strategy;

import com.fges.valueobject.Item;

import java.util.List;

public interface StorageStrategy {
    void addItem(Item item);
    void addItemList(List<Item> itemList);
    List<Item> loadAllItem();
    void deleteItem(Item item);
}
