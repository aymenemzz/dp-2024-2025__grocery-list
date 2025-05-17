package com.fges.factory;

import com.fges.application.port.out.GroceryDAO;
import com.fges.storage.CsvStorageDAO;
import com.fges.storage.JsonStorageDAO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StorageFactoryTest {

    @Test
    void should_returnCsvStorageDAO_whenTypeIsCsv() {
        GroceryDAO dao = StorageFactory.getStorage("csv", "file.csv");
        assertThat(dao).isInstanceOf(CsvStorageDAO.class);
    }

    @Test
    void should_returnJsonStorageDAO_whenTypeIsJson() {
        GroceryDAO dao = StorageFactory.getStorage("json", "file.json");
        assertThat(dao).isInstanceOf(JsonStorageDAO.class);
    }

    @Test
    void should_throwException_whenTypeIsUnsupported() {
        assertThatThrownBy(() -> StorageFactory.getStorage("xml", "file.xml"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Type de stockage non supporté");
    }

    @Test
    void should_ignoreCaseInStorageType() {
        GroceryDAO dao = StorageFactory.getStorage("JsOn", "file.json");
        assertThat(dao).isInstanceOf(JsonStorageDAO.class);
    }
}
