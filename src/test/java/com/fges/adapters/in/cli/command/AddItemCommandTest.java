package com.fges.adapters.in.cli.command;

import com.fges.application.service.GroceryListServiceImpl;
import com.fges.domain.GroceryItem;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AddItemCommandTest {

    @Test
    void should_callServiceToAddItem() {
        // Arrange
        GroceryListServiceImpl service = mock(GroceryListServiceImpl.class);
        AddItemCommand command = new AddItemCommand(service, "apple", 3, "fruits");

        // Act
        int result = command.execute();

        // Assert
        ArgumentCaptor<GroceryItem> itemCaptor = ArgumentCaptor.forClass(GroceryItem.class);
        verify(service, times(1)).addItem(itemCaptor.capture());

        GroceryItem addedItem = itemCaptor.getValue();
        assertThat(addedItem.getName()).isEqualTo("apple");
        assertThat(addedItem.getQuantity()).isEqualTo(3);
        assertThat(addedItem.getCategory()).isEqualTo("fruits");

        assertThat(result).isEqualTo(0);
    }
}
