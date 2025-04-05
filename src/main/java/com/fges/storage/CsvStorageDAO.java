package com.fges.storage;

import com.fges.storage.dao.GenericDAO;
import com.fges.valueobject.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvStorageDAO implements GenericDAO {
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
    public void addItem(Item item) {
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
                if (parts.length >= 3 && parts[0].equals(item.getName()) && parts[2].equals(item.getCategory())) {
                    int newQuantity = Integer.parseInt(parts[1]) + item.getQuantity();
                    updatedLines.add(parts[0] + "," + newQuantity + "," + parts[2]);
                    itemUpdated = true;
                } else {
                    updatedLines.add(lines.get(i));
                }
            }

            if (!itemUpdated) {
                updatedLines.add(item.getName() + "," + item.getQuantity() + "," + item.getCategory());
            }

            Files.write(storagePath, updatedLines);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'ajout ou de la mise à jour de l'item dans le fichier CSV", e);
        }
    }

    @Override
    public void addItemList(List<Item> itemList) {
        try (FileWriter writer = new FileWriter(storagePath.toFile(), true)) {
            for (Item item : itemList) {
                writer.append(item.getName()).append(",").append(String.valueOf(item.getQuantity())).append(",").append(item.getCategory()).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'écriture de la liste d'items dans le fichier CSV", e);
        }
    }

    @Override
    public List<Item> loadAllItem() {
        if (!Files.exists(storagePath) || storagePath.toFile().length() == 0) {
            return new ArrayList<>();
        }

        try {
            return Files.lines(storagePath)
                    .skip(1) // Ignorer l'en-tête
                    .map(line -> {
                        String[] parts = line.split(",");
                        return new Item(parts[0], Integer.parseInt(parts[1]), parts[2]);
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV", e);
        }
    }

    @Override
    public void deleteItem(Item item) {
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
                if (parts[0].equals(item.getName())) {
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
