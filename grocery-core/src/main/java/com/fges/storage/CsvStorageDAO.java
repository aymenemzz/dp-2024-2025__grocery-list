package com.fges.storage;

import com.fges.application.port.out.GroceryDAO;
import com.fges.domain.GroceryItem;
import com.fges.domain.GroceryList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvStorageDAO implements GroceryDAO {
    private final Path storagePath;

    public CsvStorageDAO(String filename) {
        this.storagePath = Paths.get(filename);
        File file = storagePath.toFile();

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    try (FileWriter writer = new FileWriter(file, true)) {
                        writer.append("name,quantity,category\n");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la création du fichier CSV", e);
            }
        }
    }

    @Override
    public void addItem(GroceryItem groceryItem) {
        try {
            List<String> lines = Files.readAllLines(storagePath);
            List<String> updatedLines = new ArrayList<>();
            boolean itemUpdated = false;

            // Ajouter l'en-tête s'il existe
            if (!lines.isEmpty()) {
                updatedLines.add(lines.get(0));
            } else {
                updatedLines.add("name,quantity,category");
            }

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length >= 3 && parts[0].equals(groceryItem.getName()) && parts[2].equals(groceryItem.getCategory())) {
                    int newQuantity = Integer.parseInt(parts[1]) + groceryItem.getQuantity();
                    updatedLines.add(parts[0] + "," + newQuantity + "," + parts[2]);
                    itemUpdated = true;
                } else {
                    updatedLines.add(lines.get(i));
                }
            }

            if (!itemUpdated) {
                updatedLines.add(groceryItem.getName() + "," + groceryItem.getQuantity() + "," + groceryItem.getCategory());
            }

            Files.write(storagePath, updatedLines);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'ajout ou de la mise à jour de l'item dans le fichier CSV", e);
        }
    }

    @Override
    public void addItemList(GroceryList groceryList) {
        try (FileWriter writer = new FileWriter(storagePath.toFile(), true)) {
            for (GroceryItem groceryItem : groceryList.getGroceryItemList()) {
                writer.append(groceryItem.getName()).append(",").append(String.valueOf(groceryItem.getQuantity())).append(",").append(groceryItem.getCategory()).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'écriture de la liste d'items dans le fichier CSV", e);
        }
    }

    @Override
    public GroceryList loadAllItem() {
        if (!Files.exists(storagePath) || storagePath.toFile().length() == 0) {
            return new GroceryList(new ArrayList<>());
        }

        try {
            GroceryList groceryList = new GroceryList(new ArrayList<>());
            Files.lines(storagePath)
                    .skip(1) // Ignorer l'en-tête
                    .map(line -> {
                        String[] parts = line.split(",");
                        return new GroceryItem(parts[0], Integer.parseInt(parts[1]), parts[2]);
                    })
                    .forEach(groceryList::addToList);
            return groceryList;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV", e);
        }
    }

    @Override
    public void deleteItem(GroceryItem groceryItem) {
        if (!Files.exists(storagePath)) {
            throw new RuntimeException("Le fichier CSV n'existe pas.");
        }

        try {
            List<String> lines = Files.readAllLines(storagePath);
            List<String> updatedLines = new ArrayList<>();
            updatedLines.add("name,quantity,category"); // Garder l'en-tête

            boolean removed = false;
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts[0].equals(groceryItem.getName())) {
                    removed = true;
                    continue;
                }
                updatedLines.add(lines.get(i));
            }

            if (!removed) {
                throw new IllegalArgumentException("L'item à supprimer n'existe pas !");
            }

            Files.write(storagePath, updatedLines);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'item dans le fichier CSV", e);
        }
    }
}
