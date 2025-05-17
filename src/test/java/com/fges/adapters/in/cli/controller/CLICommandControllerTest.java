
package com.fges.adapters.in.cli.controller;

import com.fges.adapters.in.cli.command.Command;
import com.fges.application.service.GroceryListServiceImpl;
import com.fges.factory.CommandFactory;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CLICommandControllerTest {

    @Test
    void should_return1_whenArgsAreEmpty() {
        GroceryListServiceImpl service = mock(GroceryListServiceImpl.class);
        CLICommandController controller = new CLICommandController(service, List.of(), "default");

        int result = controller.executeCommand();

        assertThat(result).isEqualTo(1);
    }

    @Test
    void should_executeCommandSuccessfully() {
        GroceryListServiceImpl service = mock(GroceryListServiceImpl.class);
        Command mockCommand = mock(Command.class);
        when(mockCommand.execute()).thenReturn(0);

        try (MockedStatic<CommandFactory> mockedFactory = mockStatic(CommandFactory.class)) {
            mockedFactory.when(() -> CommandFactory.getCommand(eq("list"), eq(service), eq(List.of()), eq("fruits")))
                    .thenReturn(mockCommand);

            CLICommandController controller = new CLICommandController(service, List.of("list"), "fruits");
            int result = controller.executeCommand();

            assertThat(result).isEqualTo(0);
            verify(mockCommand).execute();
        }
    }

    @Test
    void should_handleException_whenFactoryFails() {
        GroceryListServiceImpl service = mock(GroceryListServiceImpl.class);

        try (MockedStatic<CommandFactory> mockedFactory = mockStatic(CommandFactory.class)) {
            mockedFactory.when(() -> CommandFactory.getCommand(anyString(), any(), any(), any()))
                    .thenThrow(new RuntimeException("command error"));

            CLICommandController controller = new CLICommandController(service, List.of("add"), "fruits");
            int result = controller.executeCommand();

            assertThat(result).isEqualTo(1);
        }
    }
}
