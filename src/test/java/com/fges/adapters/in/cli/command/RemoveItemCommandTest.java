
package com.fges.adapters.in.cli.command;

import com.fges.application.service.GroceryListServiceImpl;
import com.fges.domain.GroceryItem;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RemoveItemCommandTest {

    @Test
    void should_callServiceToDeleteItem() {
        // Arrange
        GroceryListServiceImpl service = mock(GroceryListServiceImpl.class);
        RemoveItemCommand command = new RemoveItemCommand(service, "apple");

        // Act
        int result = command.execute();

        // Assert
        ArgumentCaptor<GroceryItem> itemCaptor = ArgumentCaptor.forClass(GroceryItem.class);
        verify(service, times(1)).deleteItem(itemCaptor.capture());

        GroceryItem deletedItem = itemCaptor.getValue();
        assertThat(deletedItem.getName()).isEqualTo("apple");
        assertThat(deletedItem.getQuantity()).isEqualTo(0);
        assertThat(deletedItem.getCategory()).isEqualTo("default");

        assertThat(result).isEqualTo(0);
    }
}
