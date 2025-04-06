package com.fges.storage;

import com.fges.valueobject.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonStorageDAOTest {
    private static final String TEST_FILE = "test_storage.json";
    private JsonStorageDAO jsonStorageDAO;

    @BeforeEach
    void setUp() {
        jsonStorageDAO = new JsonStorageDAO(TEST_FILE);
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
        assertEquals(Path.of(TEST_FILE), jsonStorageDAO.getStoragePath());
    }

    @Test
    @DisplayName("Exception levée si le nom du fichier est null ou vide")
    void shouldThrowExceptionWhenFilenameIsInvalid() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new JsonStorageDAO(null));
        assertEquals("Le nom du fichier ne peut pas être nul ou vide.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new JsonStorageDAO(""));
        assertEquals("Le nom du fichier ne peut pas être nul ou vide.", exception2.getMessage());
    }

    @Test
    @DisplayName("Ajout d'un item unique dans le fichier JSON")
    void shouldAddItem() {
        Item item = new Item("Lait", 2, "Boisson");
        jsonStorageDAO.addItem(item);
        var items = jsonStorageDAO.loadAllItem();

        assertEquals(1, items.size());
        assertEquals("Lait", items.get(0).getName());
        assertEquals(2, items.get(0).getQuantity());
        assertEquals("Boisson", items.get(0).getCategory());
    }

    @Test
    @DisplayName("Ajout d'une liste d'items")
    void shouldAddItemList() {
        Item item1 = new Item("Pain", 1, "Boulangerie");
        Item item2 = new Item("Jus", 3, "Boisson");

        jsonStorageDAO.addItemList(List.of(item1, item2));
        var items = jsonStorageDAO.loadAllItem();

        assertEquals(2, items.size());
    }

    @Test
    @DisplayName("Supprimer un item existant")
    void shouldDeleteItem() {
        Item item = new Item("Beurre", 1, "Épicerie");
        jsonStorageDAO.addItem(item);
        jsonStorageDAO.deleteItem(item);

        var items = jsonStorageDAO.loadAllItem();
        assertTrue(items.isEmpty());
    }

    @Test
    @DisplayName("Supprimer un item inexistant lève une exception")
    void shouldThrowWhenDeletingNonExistingItem() {
        Item item = new Item("Fromage", 1, "Crèmerie");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> jsonStorageDAO.deleteItem(item));
        assertEquals("L'item à supprimer n'existe pas !", exception.getMessage());
    }

    @Test
    @DisplayName("Ajouter un item déjà existant doit augmenter la quantité")
    void shouldUpdateQuantityIfItemExists() {
        Item item1 = new Item("Eau", 1, "Boisson");
        Item item2 = new Item("Eau", 2, "Boisson");

        jsonStorageDAO.addItem(item1);
        jsonStorageDAO.addItem(item2);

        var items = jsonStorageDAO.loadAllItem();
        assertEquals(1, items.size());
        assertEquals(3, items.get(0).getQuantity());
    }
}
