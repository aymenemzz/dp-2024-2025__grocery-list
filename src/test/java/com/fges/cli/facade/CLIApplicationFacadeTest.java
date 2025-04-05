package com.fges.cli.facade;

import com.fges.cli.builder.CLIApplicationBuilder;
import com.fges.cli.controller.CLICommandController;
import com.fges.serviceimpl.GroceryListServiceImpl;
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
}
