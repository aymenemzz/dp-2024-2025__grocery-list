package com.fges.cli.controller;

import com.fges.adapters.in.cli.command.Command;
import com.fges.adapters.in.cli.controller.CLICommandController;
import com.fges.application.service.GroceryListServiceImpl;
import com.fges.factory.CommandFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CLICommandControllerTest {
    private GroceryListServiceImpl groceryService;
    private CLICommandController controller;

    @BeforeEach
    void setUp() {
        groceryService = mock(GroceryListServiceImpl.class);
    }

    @Test
    @DisplayName("CLICommandController doit exécuter une commande valide")
    void shouldExecuteValidCommand() {
        List<String> args = List.of("add", "Pomme", "3");
        String category = "Fruits";
        controller = new CLICommandController(groceryService, args, category);

        // Création du mock de la commande
        Command mockCommand = mock(Command.class);
        when(mockCommand.execute()).thenReturn(0);

        try (MockedStatic<CommandFactory> mockedFactory = mockStatic(CommandFactory.class)) {
            mockedFactory.when(() -> CommandFactory.getCommand("add", groceryService, List.of("Pomme", "3"), "Fruits"))
                    .thenReturn(mockCommand);

            int result = controller.executeCommand();

            assertEquals(0, result);
            verify(mockCommand, times(1)).execute();
        }
    }

    @Test
    @DisplayName("CLICommandController doit retourner une erreur si la commande est inconnue")
    void shouldReturnErrorForUnknownCommand() {
        List<String> args = List.of("unknownCommand");
        String category = "Fruits";
        controller = new CLICommandController(groceryService, args, category);

        try (MockedStatic<CommandFactory> mockedFactory = mockStatic(CommandFactory.class)) {
            mockedFactory.when(() -> CommandFactory.getCommand("unknownCommand", groceryService, List.of(), "fruits"))
                    .thenThrow(new IllegalArgumentException("Commande inconnue"));

            int result = controller.executeCommand();

            assertEquals(1, result);
        }
    }

    @Test
    @DisplayName("CLICommandController doit retourner une erreur si aucune commande n'est fournie")
    void shouldReturnErrorForEmptyCommand() {
        List<String> args = List.of();
        String category = "Fruits";
        controller = new CLICommandController(groceryService, args, category);

        int result = controller.executeCommand();

        assertEquals(1, result);
    }
}
