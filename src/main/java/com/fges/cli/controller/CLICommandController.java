package com.fges.cli.controller;

import com.fges.cli.command.Command;
import com.fges.factory.CommandFactory;
import com.fges.serviceimpl.GroceryListServiceImpl;
import java.util.List;

public class CLICommandController {
    private final GroceryListServiceImpl groceryService;
    private final List<String> args;

    public CLICommandController(GroceryListServiceImpl groceryService, List<String> args) {
        this.groceryService = groceryService;
        this.args = args;
    }

    public int executeCommand() {
        if (args.isEmpty()) {
            System.err.println("Missing Command");
            return 1;
        }

        String commandName = args.getFirst();
        List<String> commandArgs = args.subList(1, args.size());

        try {
            Command command = CommandFactory.getCommand(commandName, groceryService, commandArgs);
            return command.execute();
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            return 1;
        }
    }
}