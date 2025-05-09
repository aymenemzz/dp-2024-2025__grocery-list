package com.fges.serviceimpl;

import com.fges.storage.dao.GroceryDAO;
import com.fges.valueobject.GroceryItem;
import com.fges.valueobject.GroceryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroceryListServiceImplTest {

    private GroceryListServiceImpl groceryListService;
    private GroceryDAO groceryDAO;

}