package com.fges.factory;

import com.fges.storage.CsvStorageDAO;
import com.fges.storage.JsonStorageDAO;
import com.fges.storage.dao.GenericDAO;

public class StorageFactory {

    public static GenericDAO getStorage(String type, String filename) {
        return switch (type.toLowerCase()) {
            case "csv" -> new CsvStorageDAO(filename);
            case "json" -> new JsonStorageDAO(filename);
            default -> throw new IllegalArgumentException("Type de stockage non supporté: " + type);
        };
    }
}