package com.fges.application.port.out;

import com.fges.domain.GroceryItem;
import com.fges.domain.GroceryList;

public interface GroceryDAO {
    void addItem(GroceryItem groceryItem);
    void addItemList(GroceryList groceryList);
    GroceryList loadAllItem();
    void deleteItem(GroceryItem groceryItem);
}
