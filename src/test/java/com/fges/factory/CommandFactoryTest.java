package com.fges.factory;

import com.fges.adapters.in.cli.command.*;
import com.fges.application.service.GroceryListServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class CommandFactoryTest {

    private final GroceryListServiceImpl service = mock(GroceryListServiceImpl.class);

    @Test
    void should_createAddCommand_withValidArgs() {
        Command command = CommandFactory.getCommand("add", service, List.of("apple", "2"), "fruit");
        assertThat(command).isInstanceOf(AddItemCommand.class);
    }

    @Test
    void should_createListCommand() {
        Command command = CommandFactory.getCommand("list", service, List.of(), "any");
        assertThat(command).isInstanceOf(ListItemsCommand.class);
    }

    @Test
    void should_createRemoveCommand_withValidArgs() {
        Command command = CommandFactory.getCommand("remove", service, List.of("milk"), "any");
        assertThat(command).isInstanceOf(RemoveItemCommand.class);
    }

    @Test
    void should_createWebCommand_withValidPort() {
        Command command = CommandFactory.getCommand("web", service, List.of("8080"), "any");
        assertThat(command).isInstanceOf(WebCommand.class);
    }

    @Test
    void should_createInfoCommand() {
        Command command = CommandFactory.getCommand("info", service, List.of(), "any");
        assertThat(command).isInstanceOf(InfoCommand.class);
    }

    @Test
    void should_throw_whenCommandIsUnknown() {
        assertThatThrownBy(() -> CommandFactory.getCommand("foo", service, List.of(), "any"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Commande inconnue");
    }

    @Test
    void should_throw_whenAddCommandHasTooFewArgs() {
        assertThatThrownBy(() -> CommandFactory.getCommand("add", service, List.of("apple"), "fruit"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("manquants");
    }

    @Test
    void should_throw_whenRemoveCommandHasNoArgs() {
        assertThatThrownBy(() -> CommandFactory.getCommand("remove", service, List.of(), "any"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("manquants");
    }

    @Test
    void should_throw_whenWebCommandHasInvalidPort() {
        assertThatThrownBy(() -> CommandFactory.getCommand("web", service, List.of("notANumber"), "any"))
                .isInstanceOf(NumberFormatException.class);
    }
}
