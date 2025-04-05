package com.fges.factory;

import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.storage.dao.GenericDAO;


public class GroceryAppFactory {
    public static GroceryListServiceImpl createGroceryApp(String storageType, String fileName) {
        GenericDAO genericDAO = StorageFactory.getStorage(storageType, fileName);
        return new GroceryListServiceImpl(genericDAO);
    }
}