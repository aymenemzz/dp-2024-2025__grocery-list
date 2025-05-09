package com.fges.application.port.in;



import com.fges.domain.GroceryItem;
import com.fges.domain.GroceryList;

import java.util.List;

public interface GroceryListService {

    void addItem(GroceryItem groceryItem);
    void deleteItem(GroceryItem groceryItem);
    GroceryList getAllItems();
}
