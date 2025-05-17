package com.fges.storage;

import com.fges.domain.GroceryItem;
import com.fges.domain.GroceryList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CsvStorageDAOTest {

    @TempDir
    Path tempDir;

    @Test
    void should_addAndLoadItemCorrectly() {
        Path filePath = tempDir.resolve("grocery.csv");
        CsvStorageDAO dao = new CsvStorageDAO(filePath.toString());

        GroceryItem item = new GroceryItem("apple", 2, "fruit");
        dao.addItem(item);

        GroceryList loaded = dao.loadAllItem();
        assertThat(loaded.getGroceryItemList()).hasSize(1);
        GroceryItem loadedItem = loaded.getGroceryItemList().get(0);
        assertThat(loadedItem.getName()).isEqualTo("apple");
        assertThat(loadedItem.getQuantity()).isEqualTo(2);
        assertThat(loadedItem.getCategory()).isEqualTo("fruit");
    }

    @Test
    void should_updateItemQuantity_whenItemExists() {
        Path filePath = tempDir.resolve("grocery.csv");
        CsvStorageDAO dao = new CsvStorageDAO(filePath.toString());

        dao.addItem(new GroceryItem("banana", 1, "fruit"));
        dao.addItem(new GroceryItem("banana", 2, "fruit")); // should update to 3

        GroceryList loaded = dao.loadAllItem();
        assertThat(loaded.getGroceryItemList()).hasSize(1);
        assertThat(loaded.getGroceryItemList().get(0).getQuantity()).isEqualTo(3);
    }

    @Test
    void should_deleteItem_whenItExists() {
        Path filePath = tempDir.resolve("grocery.csv");
        CsvStorageDAO dao = new CsvStorageDAO(filePath.toString());

        dao.addItem(new GroceryItem("milk", 1, "dairy"));
        dao.deleteItem(new GroceryItem("milk", 0, "dairy"));

        GroceryList loaded = dao.loadAllItem();
        assertThat(loaded.getGroceryItemList()).isEmpty();
    }

    @Test
    void should_throwException_whenDeletingNonExistentItem() {
        Path filePath = tempDir.resolve("grocery.csv");
        CsvStorageDAO dao = new CsvStorageDAO(filePath.toString());

        assertThatThrownBy(() -> dao.deleteItem(new GroceryItem("bread", 0, "bakery")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("n'existe pas");
    }

    @Test
    void should_addItemList_andLoadThem() {
        Path filePath = tempDir.resolve("grocery.csv");
        CsvStorageDAO dao = new CsvStorageDAO(filePath.toString());

        GroceryList list = new GroceryList(new ArrayList<>());
        list.addToList(new GroceryItem("carrot", 2, "veg"));
        list.addToList(new GroceryItem("onion", 3, "veg"));
        dao.addItemList(list);

        GroceryList loaded = dao.loadAllItem();
        assertThat(loaded.getGroceryItemList()).hasSize(2);
        assertThat(loaded.getGroceryItemList()).extracting("name")
                .containsExactlyInAnyOrder("carrot", "onion");
    }

    @Test
    void should_returnEmptyList_whenFileDoesNotExistOrIsEmpty() throws IOException {
        Path filePath = tempDir.resolve("empty.csv");
        Files.createFile(filePath); // create an empty file
        CsvStorageDAO dao = new CsvStorageDAO(filePath.toString());

        GroceryList loaded = dao.loadAllItem();
        assertThat(loaded.getGroceryItemList()).isEmpty();
    }



    @Test
    void should_throwRuntimeException_whenAddItemFailsToWriteFile() throws IOException {
        Path filePath = tempDir.resolve("bad.csv");
        Files.write(filePath, List.of("invalid header"));

        CsvStorageDAO dao = new CsvStorageDAO(filePath.toString());
        Files.delete(filePath); // Supprime le fichier après l'initialisation pour provoquer l'erreur à l'écriture

        assertThatThrownBy(() -> dao.addItem(new GroceryItem("fail", 1, "fail")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Erreur lors de l'ajout ou de la mise à jour");
    }

    @Test
    void should_throwRuntimeException_whenDeleteFailsToAccessFile() {
        Path filePath = tempDir.resolve("cannotdelete.csv");
        CsvStorageDAO dao = new CsvStorageDAO(filePath.toString());

        // Supprimer le fichier pour simuler une erreur d'accès
        filePath.toFile().delete();

        assertThatThrownBy(() -> dao.deleteItem(new GroceryItem("ghost", 0, "missing")))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("n'existe pas");
    }
}