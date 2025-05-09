package com.fges.storage;

import com.fges.valueobject.GroceryItem;
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

}
