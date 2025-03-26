package com.fges.factory;

import com.fges.dao.GroceryDAO;
import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.storage.JsonStorage;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroceryAppFactory {
    public static GroceryListServiceImpl createGroceryApp(String fileName) {
        JsonStorage jsonStorage = new JsonStorage(fileName);
        GroceryDAO groceryDAO = new GroceryDAO(jsonStorage);
        return new GroceryListServiceImpl(groceryDAO);
    }
}