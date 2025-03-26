package com.fges.serviceimpl;

import com.fges.dao.GroceryDAO;
import com.fges.valueobject.Item;
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

    @BeforeEach
    void setUp() {
        groceryDAO = mock(GroceryDAO.class);
        groceryListService = new GroceryListServiceImpl(groceryDAO);
    }

    @Test
    @DisplayName("Ajouter un item valide doit appeler le DAO")
    void addItem_ValidItem_ShouldCallDAO() {
        // Given
        Item item = new Item("Milk", 2);

        // When
        groceryListService.addItem(item);

        // Then
        verify(groceryDAO, times(1)).addItem(item);
    }

    @Test
    @DisplayName("Ajouter un item nul doit lever une exception")
    void addItem_NullItem_ShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groceryListService.addItem(null);
        });

        assertEquals("Impossible d'ajouter un item nul ou avec un nom vide !", exception.getMessage());
        verify(groceryDAO, never()).addItem(any());
    }

    @Test
    @DisplayName("Ajouter un item avec un nom vide doit lever une exception")
    void addItem_BlankName_ShouldThrowException() {
        Item item = new Item("", 2);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groceryListService.addItem(item);
        });

        assertEquals("Impossible d'ajouter un item nul ou avec un nom vide !", exception.getMessage());
        verify(groceryDAO, never()).addItem(any());
    }

    @Test
    @DisplayName("Supprimer un item valide doit appeler le DAO")
    void deleteItem_ValidItem_ShouldCallDAO() {
        // Given
        Item item = new Item("Milk", 2);

        // When
        groceryListService.deleteItem(item);

        // Then
        verify(groceryDAO, times(1)).deleteItem(item);
    }

    @Test
    @DisplayName("Supprimer un item nul doit lever une exception")
    void deleteItem_NullItem_ShouldThrowException() {
        Item itemNull = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groceryListService.deleteItem(itemNull);
        });

        assertEquals("Impossible de supprimer un item nul !", exception.getMessage());
        verify(groceryDAO, never()).deleteItem(any());
    }

    @Test
    @DisplayName("Récupérer tous les items doit retourner les items du DAO")
    void getAllItems_ShouldReturnItemsFromDAO() {
        // Given
        List<Item> mockItems = Arrays.asList(new Item("Milk", 2), new Item("Eggs", 10));
        when(groceryDAO.loadItems()).thenReturn(mockItems);

        // When
        List<Item> items = groceryListService.getAllItems();

        // Then
        assertEquals(2, items.size());
        assertEquals("Milk", items.get(0).getName());
        assertEquals(2, items.get(0).getQuantity());
        verify(groceryDAO, times(1)).loadItems();
    }

    @Test
    @DisplayName("addItem - Une exception levée par le DAO doit être encapsulée dans une RuntimeException")
    void addItem_WhenDAOThrowsException_ShouldThrowRuntimeException() {
        // Given
        Item item = new Item("Milk", 2);
        doThrow(new RuntimeException("Erreur DAO")).when(groceryDAO).addItem(item);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> groceryListService.addItem(item));
        assertEquals("Impossible d'appeler le groceryDAO pour ajouter un item", exception.getMessage());
    }

    @Test
    @DisplayName("deleteItem - Une exception levée par le DAO doit être encapsulée dans une RuntimeException")
    void deleteItem_WhenDAOThrowsException_ShouldThrowRuntimeException() {
        // Given
        Item item = new Item("Milk", 2);
        doThrow(new RuntimeException("Erreur DAO")).when(groceryDAO).deleteItem(item);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> groceryListService.deleteItem(item));
        assertEquals("Impossible d'appeler le groceryDAO pour supprimer un item", exception.getMessage());
    }

    @Test
    @DisplayName("getAllItems - Une exception levée par le DAO doit être encapsulée dans une RuntimeException")
    void getAllItems_WhenDAOThrowsException_ShouldThrowRuntimeException() {
        // Given
        doThrow(new RuntimeException("Erreur DAO")).when(groceryDAO).loadItems();

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> groceryListService.getAllItems());
        assertEquals("Impossible de récupérer la liste des items depuis le groceryDAO", exception.getMessage());
    }
}