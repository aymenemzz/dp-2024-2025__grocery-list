package com.fges.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.storage.dao.GroceryDAO;
import com.fges.valueobject.GroceryItem;
import com.fges.valueobject.GroceryList;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonStorageDAO implements GroceryDAO {
    private static String filename;
    @Getter
    private final Path storagePath;
    private final ObjectMapper objectMapper;

    public JsonStorageDAO(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Le nom du fichier ne peut pas être nul ou vide.");
        }
        JsonStorageDAO.filename = filename;
        this.storagePath = Paths.get(JsonStorageDAO.filename);
        this.objectMapper = new ObjectMapper();

        File file = storagePath.toFile();
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IOException("Impossible de créer le fichier.");
                }
                System.err.println("Fichier JSON créé : " + file.getAbsolutePath());
                saveItems(new GroceryList(new ArrayList<>())); // Initialise le fichier avec une liste vide
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la création du fichier : " + filename, e);
            }
        }
    }

    @Override
    public void addItem(GroceryItem groceryItem) {
        GroceryList groceryList = loadAllItem();

        // Vérifie si l'élément existe déjà et met à jour la quantité si nécessaire
        boolean itemExists = false;
        for (GroceryItem existingGroceryItem : groceryList.getGroceryItemList()) {
            if (existingGroceryItem.getName().equals(groceryItem.getName()) &&
                existingGroceryItem.getCategory().equals(groceryItem.getCategory())) {
                existingGroceryItem.setQuantity(existingGroceryItem.getQuantity() + groceryItem.getQuantity());
                itemExists = true;
                break;
            }
        }

        // Si l'élément n'existe pas, on l'ajoute
        if (!itemExists) {
            groceryList.addToList(new GroceryItem(groceryItem.getName(), groceryItem.getQuantity(), groceryItem.getCategory()));
        }

        saveItems(groceryList);
    }

    @Override
    public void addItemList(GroceryList groceryItemList) {
        GroceryList groceryList = groceryItemList;
        saveItems(groceryList);
    }

    @Override
    public GroceryList loadAllItem() {
        if (!Files.exists(storagePath) || storagePath.toFile().length() == 0) {
            return new GroceryList(new ArrayList<>());
        }

        try {
            Map<String, Map<String, Integer>> categorizedItems = objectMapper.readValue(
                    storagePath.toFile(),
                    objectMapper.getTypeFactory().constructMapType(Map.class,
                            objectMapper.getTypeFactory().constructType(String.class),
                            objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Integer.class))
            );

            GroceryList groceryList = new GroceryList(new ArrayList<>());
            for (Map.Entry<String, Map<String, Integer>> categoryEntry : categorizedItems.entrySet()) {
                String category = categoryEntry.getKey();
                for (Map.Entry<String, Integer> itemEntry : categoryEntry.getValue().entrySet()) {
                    groceryList.addToList(new GroceryItem(itemEntry.getKey(), itemEntry.getValue(), category));
                }
            }

            return groceryList;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier JSON : " + storagePath, e);
        }
    }

    @Override
    public void deleteItem(GroceryItem groceryItem) {
        GroceryList groceryList = loadAllItem();
        boolean removed = groceryList.getGroceryItemList().removeIf(existingItem -> existingItem.getName().equals(groceryItem.getName()));

        if (removed) {
            saveItems(groceryList);
        } else {
            throw new IllegalArgumentException("L'item à supprimer n'existe pas !");
        }
    }

    private void saveItems(GroceryList groceryList) {
        try {
            Map<String, Map<String, Integer>> categorizedItems = new HashMap<>();
            for (GroceryItem groceryItem : groceryList.getGroceryItemList()) {
                categorizedItems
                        .computeIfAbsent(groceryItem.getCategory(), k -> new HashMap<>())
                        .put(groceryItem.getName(), groceryItem.getQuantity());
            }
            objectMapper.writeValue(storagePath.toFile(), categorizedItems);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'écriture dans le fichier JSON : " + storagePath, e);
        }
    }
}