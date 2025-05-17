package com.fges.factory;

import com.fges.application.service.GroceryListServiceImpl;
import com.fges.application.port.out.GroceryDAO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GroceryAppFactoryTest {

    @Test
    void should_createGroceryListService_withGivenStorageTypeAndFileName() {
        try (MockedStatic<StorageFactory> mocked = mockStatic(StorageFactory.class)) {
            GroceryDAO mockDao = mock(GroceryDAO.class);
            mocked.when(() -> StorageFactory.getStorage("json", "test.json")).thenReturn(mockDao);

            GroceryListServiceImpl service = GroceryAppFactory.createGroceryApp("json", "test.json");

            assertThat(service).isNotNull();
            assertThat(service).isInstanceOf(GroceryListServiceImpl.class);
        }
    }
}
