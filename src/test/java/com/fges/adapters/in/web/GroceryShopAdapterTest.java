package com.fges.adapters.in.web;

import com.fges.adapters.in.web.dto.WebGroceryItemDTO;
import com.fges.adapters.in.web.mapper.WebGroceryItemDTOMapper;
import com.fges.application.port.in.GroceryListService;
import com.fges.domain.GroceryItem;
import com.fges.domain.GroceryList;
import fr.anthonyquere.MyGroceryShop.WebGroceryItem;
import fr.anthonyquere.MyGroceryShop.Runtime;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GroceryShopAdapterTest {

    @Test
    void should_returnListOfGroceries() {
        GroceryItem item = new GroceryItem("apple", 2, "fruit");
        GroceryList list = new GroceryList(new ArrayList<>());
        list.addToList(item);

        GroceryListService service = mock(GroceryListService.class);
        when(service.getAllItems()).thenReturn(list);

        GroceryShopAdapter adapter = new GroceryShopAdapter(service);
        List<WebGroceryItem> groceries = adapter.getGroceries();

        assertThat(groceries).hasSize(1);
        WebGroceryItem webItem = groceries.get(0);
        assertThat(webItem.name()).isEqualTo("apple");
        assertThat(webItem.quantity()).isEqualTo(2);
        assertThat(webItem.category()).isEqualTo("fruit");
    }

    @Test
    void should_addItemToService() {
        GroceryListService service = mock(GroceryListService.class);
        GroceryShopAdapter adapter = new GroceryShopAdapter(service);

        adapter.addGroceryItem("milk", 1, "dairy");

        verify(service).addItem(argThat(item ->
            item.getName().equals("milk") &&
            item.getQuantity() == 1 &&
            item.getCategory().equals("dairy")
        ));
    }

    @Test
    void should_removeItemFromService() {
        GroceryListService service = mock(GroceryListService.class);
        GroceryShopAdapter adapter = new GroceryShopAdapter(service);

        adapter.removeGroceryItem("banana");

        verify(service).deleteItem(argThat(item ->
            item.getName().equals("banana") &&
            item.getQuantity() == 0 &&
            item.getCategory().equals("")
        ));
    }

    @Test
    void should_returnCurrentRuntimeInfo() {
        GroceryListService service = mock(GroceryListService.class);
        GroceryShopAdapter adapter = new GroceryShopAdapter(service);

        Runtime runtime = adapter.getRuntime();

        assertThat(runtime).isNotNull();
        assertThat(runtime.todayDate()).isEqualTo(LocalDate.now());
        assertThat(runtime.javaVersion()).isEqualTo(System.getProperty("java.version"));
        assertThat(runtime.osName()).isEqualTo(System.getProperty("os.name"));
    }
}
