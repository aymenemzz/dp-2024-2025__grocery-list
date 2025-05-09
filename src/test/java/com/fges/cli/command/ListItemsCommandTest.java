package com.fges.cli.command;

import com.fges.adapters.in.cli.command.ListItemsCommand;
import com.fges.application.service.GroceryListServiceImpl;
import com.fges.domain.GroceryItem;
import com.fges.domain.GroceryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class ListItemsCommandTest {
    private GroceryListServiceImpl groceryService;
    private ListItemsCommand listItemsCommand;

    @BeforeEach
    void setUp() {
        groceryService = mock(GroceryListServiceImpl.class);
    }

    @Test
    @DisplayName("ListItemsCommand doit appeler getAllItems du service")
    void listItemsCommand_ShouldCallService() {
        // Given
        when(groceryService.getAllItems()).thenReturn(new GroceryList(List.of(new GroceryItem("Pomme", 3, "fruits"))));

        listItemsCommand = new ListItemsCommand(groceryService);

        //Act
        listItemsCommand.execute();

        // Then
        verify(groceryService, times(1)).getAllItems();
    }
}