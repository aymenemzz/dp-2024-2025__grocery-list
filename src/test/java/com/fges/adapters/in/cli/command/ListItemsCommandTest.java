package com.fges.adapters.in.cli.command;

import com.fges.application.service.GroceryListServiceImpl;
import com.fges.domain.GroceryItem;
import com.fges.domain.GroceryList;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ListItemsCommandTest {

    @Test
    void should_printGroupedItemsByCategory() {
        // Préparer le mock du service
        GroceryListServiceImpl service = mock(GroceryListServiceImpl.class);
        GroceryList mockList = new GroceryList(new ArrayList<>());
        mockList.addToList(new GroceryItem("apple", 2, "fruits"));
        mockList.addToList(new GroceryItem("banana", 3, "fruits"));
        mockList.addToList(new GroceryItem("milk", 1, "dairy"));
        when(service.getAllItems()).thenReturn(mockList);

        // Capturer la sortie système
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Exécuter la commande
            ListItemsCommand command = new ListItemsCommand(service);
            int result = command.execute();

            // Vérifier le résultat
            String output = outContent.toString();
            assertThat(output).contains("fruits :", "\tapple : 2", "\tbanana : 3", "dairy :", "\tmilk : 1");
            assertThat(result).isEqualTo(0);
        } finally {
            System.setOut(originalOut);
        }
    }
}
