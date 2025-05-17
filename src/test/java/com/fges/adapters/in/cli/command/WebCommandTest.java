package com.fges.adapters.in.cli.command;

import com.fges.adapters.in.web.GroceryShopAdapter;
import com.fges.adapters.in.web.server.WebServerLauncher;
import com.fges.application.port.in.GroceryListService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WebCommandTest {

    @Test
    void should_launchWebServerAndReturnZero() {
        // Mock du service
        GroceryListService service = mock(GroceryListService.class);

        try (MockedConstruction<WebServerLauncher> mocked = mockConstruction(WebServerLauncher.class,
                (mock, context) -> doNothing().when(mock).launch(anyInt(), any(GroceryShopAdapter.class)))) {

            // Création de la commande
            WebCommand command = new WebCommand(service, 1234);

            // Exécution
            int result = command.execute();

            // Vérifications
            WebServerLauncher launcher = mocked.constructed().get(0);
            verify(launcher).launch(eq(1234), any(GroceryShopAdapter.class));

            assertThat(result).isEqualTo(0);
        }
    }
}
