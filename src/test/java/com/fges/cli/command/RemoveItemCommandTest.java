package com.fges.cli.command;

import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.valueobject.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RemoveItemCommandTest {
    private GroceryListServiceImpl groceryService;
    private RemoveItemCommand removeItemCommand;

    @BeforeEach
    void setUp() {
        groceryService = mock(GroceryListServiceImpl.class);
    }

    @Test
    @DisplayName("RemoveItemCommand doit appeler deleteItem du service")
    void removeItemCommand_ShouldCallService() {
        // Given
        removeItemCommand = new RemoveItemCommand(groceryService, "Pomme");

        // When
        int result = removeItemCommand.execute();

        // Then
        assertEquals(0, result);
        verify(groceryService, times(1)).deleteItem(any(Item.class));
    }
}