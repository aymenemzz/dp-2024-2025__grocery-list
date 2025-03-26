package com.fges.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.storage.JsonStorage;
import com.fges.valueobject.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GroceryDAO {
    private final Path filePath;
    private final ObjectMapper objectMapper;

    public GroceryDAO(JsonStorage jsonStorage) {
        this.filePath = jsonStorage.getStoragePath();
        this.objectMapper = new ObjectMapper();
    }

    private void saveItems(List<Item> itemList) {
        try {
            objectMapper.writeValue(filePath.toFile(), itemList);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'écriture du fichier JSON", e);
        }
    }

    public List<Item> loadItems() {
        try {
            if (!Files.exists(filePath) || Files.size(filePath) == 0) {
                return new ArrayList<>(); // Si le fichier n'existe pas ou est vide, retourne une liste vide
            }
            return objectMapper.readValue(filePath.toFile(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Item.class));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier JSON : " + filePath, e);
        }
    }

    public void addItem(Item item) {
        List<Item> itemList = loadItems();
        itemList.add(item);
        saveItems(itemList);
    }

    public void deleteItem(Item item) {
        List<Item> itemList = loadItems();
        boolean removed = itemList.removeIf(existingItem -> existingItem.getName().equals(item.getName()));

        if (removed) {
            saveItems(itemList);
        } else {
            throw new IllegalArgumentException("L'item à supprimer n'existe pas !");
        }
    }
}