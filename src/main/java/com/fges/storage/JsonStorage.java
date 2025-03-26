package com.fges.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.storage.strategy.StorageStrategy;
import com.fges.valueobject.Item;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage implements StorageStrategy {
    private static String filename;
    @Getter
    private final Path storagePath;
    private final ObjectMapper objectMapper;

    public JsonStorage(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Le nom du fichier ne peut pas être nul ou vide.");
        }
        JsonStorage.filename = filename;
        this.storagePath = Paths.get(JsonStorage.filename);
        this.objectMapper = new ObjectMapper();

        File file = storagePath.toFile();
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IOException("Impossible de créer le fichier.");
                }
                System.err.println("Fichier JSON créé : " + file.getAbsolutePath());
                saveItems(new ArrayList<>()); // Initialise le fichier avec une liste vide
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la création du fichier : " + filename, e);
            }
        }
    }

    @Override
    public void addItem(Item item) {
        List<Item> items = loadAllItem();

        // Vérifie si l'élément existe déjà et met à jour la quantité si nécessaire
        boolean itemExists = false;
        for (Item existingItem : items) {
            if (existingItem.getName().equals(item.getName())) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                itemExists = true;
                break;
            }
        }

        // Si l'élément n'existe pas, on l'ajoute
        if (!itemExists) {
            items.add(new Item(item.getName(), item.getQuantity()));
        }

        saveItems(items);
    }

    @Override
    public void addItemList(List<Item> itemList) {
        List<Item> items = loadAllItem();
        items.addAll(itemList);
        saveItems(items);
    }

    @Override
    public List<Item> loadAllItem() {
        if (!Files.exists(storagePath) || storagePath.toFile().length() == 0) {
            return new ArrayList<>(); // Retourne une liste vide si le fichier est inexistant ou vide
        }

        try {
            return objectMapper.readValue(storagePath.toFile(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Item.class));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier JSON : " + storagePath, e);
        }
    }

    @Override
    public void deleteItem(Item item) {
        List<Item> items = loadAllItem();
        boolean removed = items.removeIf(existingItem -> existingItem.getName().equals(item.getName()));

        if (removed) {
            saveItems(items);
        } else {
            throw new IllegalArgumentException("L'item à supprimer n'existe pas !");
        }
    }

    private void saveItems(List<Item> items) {
        try {
            objectMapper.writeValue(storagePath.toFile(), items);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'écriture dans le fichier JSON : " + storagePath, e);
        }
    }
}