package com.fges.storage;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonStorage {
    private static String filename;
    @Getter
    private final Path storagePath;

    public JsonStorage(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Le nom du fichier ne peut pas être nul ou vide.");
        }
        JsonStorage.filename = filename;
        this.storagePath = Paths.get(JsonStorage.filename);

        File file = storagePath.toFile();
        if (!file.exists()) {
            try {
                // Je n'arrive pas a tester le cas ou la creation du fichier echoue
                // si un cas d'usage est trouvé alors il faut creer le TU dans JsonStorageTest.java
                if (!file.createNewFile()) {
                    throw new IOException("Impossible de créer le fichier.");
                }
                System.err.println("Fichier créé : " + file.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la création du fichier : " + filename, e);
            }
        }
    }
}