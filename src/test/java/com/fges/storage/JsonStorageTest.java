package com.fges.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JsonStorageTest {
    private static final String TEST_FILE = "test_storage.json";
    private JsonStorage jsonStorage;

    @BeforeEach
    void setUp() {
        jsonStorage = new JsonStorage(TEST_FILE);
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    @DisplayName("Le fichier JSON doit être créé s'il n'existe pas")
    void fileShouldBeCreated() {
        assertTrue(new File(TEST_FILE).exists());
    }

    @Test
    @DisplayName("Le chemin du fichier doit être correct")
    void shouldReturnCorrectPath() {
        assertEquals(Path.of(TEST_FILE), jsonStorage.getStoragePath());
    }

    @Test
    @DisplayName("Exception levée si le nom du fichier est null ou vide")
    void shouldThrowExceptionWhenFilenameIsInvalid() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new JsonStorage(null));
        assertEquals("Le nom du fichier ne peut pas être nul ou vide.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new JsonStorage(""));
        assertEquals("Le nom du fichier ne peut pas être nul ou vide.", exception2.getMessage());
    }
}
