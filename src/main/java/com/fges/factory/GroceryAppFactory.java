package com.fges.factory;

import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.storage.dao.GroceryDAO;


public class GroceryAppFactory {
    public static GroceryListServiceImpl createGroceryApp(String storageType, String fileName) {
        GroceryDAO groceryDAO = StorageFactory.getStorage(storageType, fileName);
        return new GroceryListServiceImpl(groceryDAO);
    }
}