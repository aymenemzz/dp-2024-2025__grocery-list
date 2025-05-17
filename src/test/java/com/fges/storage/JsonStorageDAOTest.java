package com.fges.storage;

import com.fges.domain.GroceryItem;
import com.fges.domain.GroceryList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

class JsonStorageDAOTest {

    @TempDir
    Path tempDir;

    @Test
    void should_addAndLoadItemCorrectly() {
        Path file = tempDir.resolve("items.json");
        JsonStorageDAO dao = new JsonStorageDAO(file.toString());

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
    void should_mergeQuantities_whenAddingSameItem() {
        Path file = tempDir.resolve("items.json");
        JsonStorageDAO dao = new JsonStorageDAO(file.toString());

        dao.addItem(new GroceryItem("apple", 1, "fruit"));
        dao.addItem(new GroceryItem("apple", 2, "fruit"));

        GroceryList loaded = dao.loadAllItem();
        assertThat(loaded.getGroceryItemList()).hasSize(1);
        assertThat(loaded.getGroceryItemList().get(0).getQuantity()).isEqualTo(3);
    }

    @Test
    void should_deleteItem() {
        Path file = tempDir.resolve("items.json");
        JsonStorageDAO dao = new JsonStorageDAO(file.toString());

        dao.addItem(new GroceryItem("milk", 1, "dairy"));
        dao.deleteItem(new GroceryItem("milk", 0, "dairy"));

        GroceryList loaded = dao.loadAllItem();
        assertThat(loaded.getGroceryItemList()).isEmpty();
    }

    @Test
    void should_throw_whenDeletingNonexistentItem() {
        Path file = tempDir.resolve("items.json");
        JsonStorageDAO dao = new JsonStorageDAO(file.toString());

        assertThatThrownBy(() -> dao.deleteItem(new GroceryItem("banana", 0, "fruit")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("existe pas");
    }

    @Test
    void should_addItemList_andReadThem() {
        Path file = tempDir.resolve("items.json");
        JsonStorageDAO dao = new JsonStorageDAO(file.toString());

        GroceryList list = new GroceryList(new ArrayList<>());
        list.addToList(new GroceryItem("bread", 2, "bakery"));
        list.addToList(new GroceryItem("cheese", 1, "dairy"));

        dao.addItemList(list);

        GroceryList loaded = dao.loadAllItem();
        assertThat(loaded.getGroceryItemList()).hasSize(2);
    }

    @Test
    void should_returnEmptyList_whenFileIsEmpty() throws IOException {
        Path file = tempDir.resolve("items.json");
        file.toFile().createNewFile(); // fichier vide
        JsonStorageDAO dao = new JsonStorageDAO(file.toString());

        GroceryList loaded = dao.loadAllItem();
        assertThat(loaded.getGroceryItemList()).isEmpty();
    }
}
