package com.fges.storage;

import com.fges.storage.strategy.StorageStrategy;
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

public class CsvStorage implements StorageStrategy {
    private final Path storagePath;

    public CsvStorage(String filename) {
        this.storagePath = Paths.get(filename);
        File file = storagePath.toFile();

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    try (FileWriter writer = new FileWriter(file, true)) {
                        writer.append("name,quantity\n");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la création du fichier CSV", e);
            }
        }
    }

    @Override
    public void addItem(Item item) {
        try (FileWriter writer = new FileWriter(storagePath.toFile(), true)) {
            writer.append(item.getName()).append(",").append(String.valueOf(item.getQuantity())).append("\n");
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'écriture de l'item dans le fichier CSV", e);
        }
    }

    @Override
    public void addItemList(List<Item> itemList) {
        try (FileWriter writer = new FileWriter(storagePath.toFile(), true)) {
            for (Item item : itemList) {
                writer.append(item.getName()).append(",").append(String.valueOf(item.getQuantity())).append("\n");
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
                        return new Item(parts[0], Integer.parseInt(parts[1]));
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
            updatedLines.add("name,quantity"); // Garder l'en-tête

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
