package com.fges.factory;

import com.fges.dao.GroceryDAO;
import com.fges.factory.StorageFactory;
import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.storage.strategy.StorageStrategy;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroceryAppFactory {
    public static GroceryListServiceImpl createGroceryApp(String storageType, String fileName) {
        StorageStrategy storageStrategy = StorageFactory.getStorage(storageType, fileName);
        GroceryDAO groceryDAO = new GroceryDAO(storageStrategy);
        return new GroceryListServiceImpl(groceryDAO);
    }
}