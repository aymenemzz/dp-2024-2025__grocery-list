package com.fges.factory;

import com.fges.storage.CsvStorage;
import com.fges.storage.JsonStorage;
import com.fges.storage.strategy.StorageStrategy;

public class StorageFactory {

    public static StorageStrategy getStorage(String type, String filename) {
        return switch (type.toLowerCase()) {
            case "csv" -> new CsvStorage(filename);
            case "json" -> new JsonStorage(filename);
            default -> throw new IllegalArgumentException("Type de stockage non supporté: " + type);
        };
    }
}