package com.fges.dao;

import com.fges.storage.JsonStorage;
import com.fges.valueobject.Item;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroceryDAOTest {
    private static final String TEST_FILE = "test_grocery.json";
    private GroceryDAO groceryDAO;

    @BeforeEach
    void setUp() {
        JsonStorage storage = new JsonStorage(TEST_FILE);
        groceryDAO = new GroceryDAO(storage);
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(new File(TEST_FILE).toPath());
    }

    @Test
    @DisplayName("Ajouter un item dans le fichier JSON")
    void addItem_ShouldSaveToFile() {
        Item item = new Item("Pommes", 2);
        groceryDAO.addItem(item);

        List<Item> items = groceryDAO.loadItems();
        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
    }

    @Test
    @DisplayName("Récupérer un item depuis le fichier JSON")
    void loadItems_ShouldReturnSavedItems() {
        groceryDAO.addItem(new Item("Pommes", 2));
        groceryDAO.addItem(new Item("Bananes", 5));

        List<Item> items = groceryDAO.loadItems();
        assertEquals(2, items.size());
        assertEquals("Pommes", items.getFirst().getName());
        assertEquals(2, items.getFirst().getQuantity());
    }

    @Test
    @DisplayName("Supprimer un item existant doit le retirer du fichier JSON")
    void deleteItem_ShouldRemoveFromFile() {
        // Given
        Item item1 = new Item("Pommes", 2);
        Item item2 = new Item("Bananes", 5);
        groceryDAO.addItem(item1);
        groceryDAO.addItem(item2);

        // When
        groceryDAO.deleteItem(item1);

        // Then
        List<Item> items = groceryDAO.loadItems();
        assertEquals(1, items.size());
        assertEquals("Bananes", items.getFirst().getName());
    }

    @Test
    @DisplayName("Supprimer un item qui n'existe pas doit lancer une exception")
    void deleteItem_NonExistent_ShouldThrowException() {
        // Given
        groceryDAO.addItem(new Item("Pommes", 2));

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groceryDAO.deleteItem(new Item("Orange", 1)); // Cet item n'existe pas
        });

        assertEquals("L'item à supprimer n'existe pas !", exception.getMessage());
    }

    @Test
    @DisplayName("loadItems doit lever une exception en cas d'erreur de lecture du fichier")
    void loadItems_ShouldThrowException_WhenFileIsCorrupt() throws IOException {
        // Simuler un fichier corrompu en écrivant un contenu invalide
        Files.writeString(Path.of(TEST_FILE), "data corrompue");

        Exception exception = assertThrows(RuntimeException.class, () -> groceryDAO.loadItems());

        assertTrue(exception.getMessage().contains("Erreur lors de la lecture du fichier JSON"));
    }

    @Test
    @DisplayName("saveItems doit lever une exception en cas d'erreur d'écriture")
    void saveItems_ShouldThrowException_WhenFileIsNotWritable() {
        // Rendre le fichier non modifiable
        File file = new File(TEST_FILE);
        file.setReadOnly();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            groceryDAO.addItem(new Item("Pommes", 2));
        });

        assertTrue(exception.getMessage().contains("Erreur lors de l'écriture du fichier JSON"));

        // Réactiver l'écriture pour ne pas bloquer les tests suivants
        file.setWritable(true);
    }
}