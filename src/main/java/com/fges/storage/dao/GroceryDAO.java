package com.fges.storage.dao;

import com.fges.valueobject.GroceryItem;
import com.fges.valueobject.GroceryList;

public interface GroceryDAO {
    void addItem(GroceryItem groceryItem);
    void addItemList(GroceryList groceryList);
    GroceryList loadAllItem();
    void deleteItem(GroceryItem groceryItem);
}
