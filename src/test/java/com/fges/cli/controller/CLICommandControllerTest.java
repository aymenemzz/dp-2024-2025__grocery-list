package com.fges.cli.controller;

import com.fges.cli.command.Command;
import com.fges.factory.CommandFactory;
import com.fges.serviceimpl.GroceryListServiceImpl;
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
        controller = new CLICommandController(groceryService, args);

        // Création du mock de la commande
        Command mockCommand = mock(Command.class);
        when(mockCommand.execute()).thenReturn(0);

        // Utilisation de MockedStatic pour moquer la méthode statique CommandFactory.getCommand()
        try (MockedStatic<CommandFactory> mockedFactory = mockStatic(CommandFactory.class)) {
            mockedFactory.when(() -> CommandFactory.getCommand("add", groceryService, List.of("Pomme", "3")))
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
        controller = new CLICommandController(groceryService, args);

        try (MockedStatic<CommandFactory> mockedFactory = mockStatic(CommandFactory.class)) {
            mockedFactory.when(() -> CommandFactory.getCommand("unknownCommand", groceryService, List.of()))
                    .thenThrow(new IllegalArgumentException("Commande inconnue"));

            int result = controller.executeCommand();

            assertEquals(1, result);
        }
    }

    @Test
    @DisplayName("CLICommandController doit retourner une erreur si aucune commande n'est fournie")
    void shouldReturnErrorForEmptyCommand() {
        List<String> args = List.of();
        controller = new CLICommandController(groceryService, args);

        int result = controller.executeCommand();

        assertEquals(1, result);
    }
}
