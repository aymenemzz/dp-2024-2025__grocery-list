package com.fges.storage;

import com.fges.valueobject.Item;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CsvStorageDAOTest {

    private static final String TEST_FILE = "test_storage.csv";
    private CsvStorageDAO dao;

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
        dao = new CsvStorageDAO(TEST_FILE);
    }

    @Test
    @Order(1)
    @DisplayName("Ajoute un item et le récupère")
    void testAddAndLoadItem() {
        Item item = new Item("Lait", 2, "Boisson");
        dao.addItem(item);

        List<Item> items = dao.loadAllItem();
        assertEquals(1, items.size());
        assertEquals("Lait", items.get(0).getName());
        assertEquals(2, items.get(0).getQuantity());
        assertEquals("Boisson", items.get(0).getCategory());
    }

    @Test
    @Order(2)
    @DisplayName("Met à jour la quantité d'un item existant")
    void testUpdateExistingItem() {
        dao.addItem(new Item("Pain", 1, "Boulangerie"));
        dao.addItem(new Item("Pain", 2, "Boulangerie"));

        List<Item> items = dao.loadAllItem();
        assertEquals(1, items.size());
        assertEquals(3, items.get(0).getQuantity());
    }

    @Test
    @Order(3)
    @DisplayName("Ajoute une liste d'items")
    void testAddItemList() {
        dao.addItemList(List.of(
                new Item("Jus", 3, "Boisson"),
                new Item("Beurre", 1, "Épicerie")
        ));

        List<Item> items = dao.loadAllItem();
        assertEquals(2, items.size());
    }

    @Test
    @Order(4)
    @DisplayName("Supprime un item existant")
    void testDeleteExistingItem() {
        dao.addItem(new Item("Fromage", 1, "Crèmerie"));
        dao.deleteItem(new Item("Fromage", 1, "Crèmerie"));

        List<Item> items = dao.loadAllItem();
        assertTrue(items.isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("Exception si suppression item inexistant")
    void testDeleteNonExistingItem() {
        assertThrows(IllegalArgumentException.class, () ->
                dao.deleteItem(new Item("Yaourt", 1, "Crèmerie"))
        );
    }

    @Test
    @Order(6)
    @DisplayName("loadAllItem retourne une liste vide si fichier vide")
    void testLoadAllItemEmptyFile() {
        List<Item> items = dao.loadAllItem();
        assertTrue(items.isEmpty());
    }
}
