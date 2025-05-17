package com.fges.application.service;

import com.fges.application.port.out.GroceryDAO;
import com.fges.domain.GroceryItem;
import com.fges.domain.GroceryList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class GroceryListServiceImplTest {

    @Test
    void should_addItem_viaDAO() throws Exception {
        GroceryDAO dao = mock(GroceryDAO.class);
        GroceryListServiceImpl service = new GroceryListServiceImpl(dao);

        GroceryItem item = new GroceryItem("apple", 2, "fruit");
        service.addItem(item);

        verify(dao).addItem(item);
    }

    @Test
    void should_throwException_whenAddingInvalidItem() {
        GroceryDAO dao = mock(GroceryDAO.class);
        GroceryListServiceImpl service = new GroceryListServiceImpl(dao);

        assertThatThrownBy(() -> service.addItem(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nul");

        assertThatThrownBy(() -> service.addItem(new GroceryItem("   ", 1, "fruit")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("vide");
    }

    @Test
    void should_deleteItem_viaDAO() throws Exception {
        GroceryDAO dao = mock(GroceryDAO.class);
        GroceryListServiceImpl service = new GroceryListServiceImpl(dao);

        GroceryItem item = new GroceryItem("milk", 1, "dairy");
        service.deleteItem(item);

        verify(dao).deleteItem(item);
    }

    @Test
    void should_throwException_whenDeletingNullItem() {
        GroceryDAO dao = mock(GroceryDAO.class);
        GroceryListServiceImpl service = new GroceryListServiceImpl(dao);

        assertThatThrownBy(() -> service.deleteItem(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nul");
    }

    @Test
    void should_returnAllItems_fromDAO() throws Exception {
        GroceryDAO dao = mock(GroceryDAO.class);
        GroceryList list = new GroceryList(new ArrayList<>());
        list.addToList(new GroceryItem("bread", 1, "bakery"));
        when(dao.loadAllItem()).thenReturn(list);

        GroceryListServiceImpl service = new GroceryListServiceImpl(dao);
        GroceryList result = service.getAllItems();

        assertThat(result.getGroceryItemList()).hasSize(1);
        assertThat(result.getGroceryItemList().get(0).getName()).isEqualTo("bread");
    }


    @Test
    void should_throwRuntimeException_whenDaoFailsToAddItem() throws Exception {
        GroceryDAO dao = mock(GroceryDAO.class);
        GroceryListServiceImpl service = new GroceryListServiceImpl(dao);
        GroceryItem item = new GroceryItem("carrot", 1, "vegetables");

        doThrow(new RuntimeException("DAO error")).when(dao).addItem(item);

        assertThatThrownBy(() -> service.addItem(item))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Impossible d'appeler le groceryDAO");
    }

    @Test
    void should_throwRuntimeException_whenDaoFailsToDeleteItem() throws Exception {
        GroceryDAO dao = mock(GroceryDAO.class);
        GroceryListServiceImpl service = new GroceryListServiceImpl(dao);
        GroceryItem item = new GroceryItem("carrot", 1, "vegetables");

        doThrow(new RuntimeException("DAO error")).when(dao).deleteItem(item);

        assertThatThrownBy(() -> service.deleteItem(item))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Impossible d'appeler le groceryDAO");
    }
}
