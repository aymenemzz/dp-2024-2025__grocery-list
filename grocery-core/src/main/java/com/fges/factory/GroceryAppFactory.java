package com.fges.factory;

import com.fges.application.port.out.GroceryDAO;
import com.fges.application.service.GroceryListServiceImpl;


public class GroceryAppFactory {
    public static GroceryListServiceImpl createGroceryApp(String storageType, String fileName) {
        GroceryDAO groceryDAO = StorageFactory.getStorage(storageType, fileName);
        return new GroceryListServiceImpl(groceryDAO);
    }
}