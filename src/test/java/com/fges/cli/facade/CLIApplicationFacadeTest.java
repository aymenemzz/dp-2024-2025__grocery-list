package com.fges.cli.facade;

import com.fges.adapters.in.cli.builder.CLIApplicationBuilder;
import com.fges.adapters.in.cli.controller.CLICommandController;
import com.fges.adapters.in.cli.facade.CLIApplicationFacade;
import com.fges.application.service.GroceryListServiceImpl;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CLIApplicationFacadeTest {
    private CLIApplicationFacade facade;
    private GroceryListServiceImpl groceryService;

    @BeforeEach
    void setUp() {
        groceryService = mock(GroceryListServiceImpl.class);
    }

    @Test
    @DisplayName("CLIApplicationFacade doit retourner une erreur si le parsing échoue")
    void shouldReturnErrorOnParsingFailure() {
        try (MockedConstruction<CLIApplicationBuilder> mockedBuilder =
                     Mockito.mockConstruction(CLIApplicationBuilder.class,
                             (mock, context) -> {
                                 when(mock.setupOptions()).thenReturn(mock);
                                 when(mock.parseArguments()).thenThrow(new ParseException("Erreur parsing"));
                             })) {

            facade = new CLIApplicationFacade(new String[]{"--invalid"});
            int result = facade.run();

            assertEquals(1, result);
        }
    }

    @Test
    @DisplayName("CLIApplicationFacade doit exécuter avec succès une commande")
    void shouldRunSuccessfully() {
        try (MockedConstruction<CLIApplicationBuilder> mockedBuilder =
                     Mockito.mockConstruction(CLIApplicationBuilder.class,
                             (builderMock, context) -> {
                                 when(builderMock.setupOptions()).thenReturn(builderMock);
                                 when(builderMock.parseArguments()).thenReturn(builderMock);
                                 when(builderMock.buildService()).thenReturn(groceryService);
                                 when(builderMock.getParsedArgs()).thenReturn(new String[]{"add", "item"});
                                 when(builderMock.getCategory()).thenReturn("Boisson");
                             });
             MockedConstruction<CLICommandController> mockedController =
                     Mockito.mockConstruction(CLICommandController.class,
                             (controllerMock, context) -> {
                                 when(controllerMock.executeCommand()).thenReturn(0);
                             })) {

            facade = new CLIApplicationFacade(new String[]{"add", "item"});
            int result = facade.run();

            assertEquals(0, result);
        }
    }

    @Test
    @DisplayName("CLIApplicationFacade doit gérer une exception inattendue")
    void shouldHandleUnexpectedException() {
        try (MockedConstruction<CLIApplicationBuilder> mockedBuilder =
                     Mockito.mockConstruction(CLIApplicationBuilder.class,
                             (builderMock, context) -> {
                                 when(builderMock.setupOptions()).thenReturn(builderMock);
                                 when(builderMock.parseArguments()).thenReturn(builderMock);
                                 when(builderMock.buildService()).thenReturn(groceryService);
                                 when(builderMock.getParsedArgs()).thenReturn(new String[]{"oops"});
                                 when(builderMock.getCategory()).thenReturn("Divers");
                             });
             MockedConstruction<CLICommandController> mockedController =
                     Mockito.mockConstruction(CLICommandController.class,
                             (controllerMock, context) -> {
                                 when(controllerMock.executeCommand()).thenThrow(new RuntimeException("Boom"));
                             })) {

            facade = new CLIApplicationFacade(new String[]{"oops"});
            int result = facade.run();

            assertEquals(1, result);
        }
    }
}
