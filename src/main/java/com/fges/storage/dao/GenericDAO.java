package com.fges.storage.dao;

import com.fges.valueobject.Item;

import java.util.List;

public interface GenericDAO {
    void addItem(Item item);
    void addItemList(List<Item> itemList);
    List<Item> loadAllItem();
    void deleteItem(Item item);
}
