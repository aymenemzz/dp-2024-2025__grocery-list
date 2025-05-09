package com.fges.storage;

import com.fges.valueobject.GroceryItem;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CsvStorageDAOTest {

    private static final String TEST_FILE = "test_storage.csv";
    private CsvStorageDAO dao;

}
