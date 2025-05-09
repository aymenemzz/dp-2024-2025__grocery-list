package com.fges.cli.command;

import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.valueobject.GroceryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddGroceryItemCommandTest {
    private GroceryListServiceImpl groceryService;
    private AddItemCommand addItemCommand;

    @BeforeEach
    void setUp() {
        groceryService = mock(GroceryListServiceImpl.class);
    }

    @Test
    @DisplayName("AddItemCommand doit appeler addItem du service")
    void addItemCommand_ShouldCallService() {
        // Given
        addItemCommand = new AddItemCommand(groceryService, "Pomme", 3, "fruits");

        // When
        int result = addItemCommand.execute();

        // Then
        assertEquals(0, result);
        verify(groceryService, times(1)).addItem(any(GroceryItem.class));
    }
}
