package com.fges.factory;

import com.fges.cli.command.AddItemCommand;
import com.fges.cli.command.Command;
import com.fges.cli.command.ListItemsCommand;
import com.fges.cli.command.RemoveItemCommand;
import com.fges.serviceimpl.GroceryListServiceImpl;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CommandFactory {
    public static Command getCommand(String command, GroceryListServiceImpl service, List<String> args, String category) {
        switch (command) {
            case "add" -> {
                if (args.size() < 2) throw new IllegalArgumentException("Arguments manquants pour la commande add");
                return new AddItemCommand(service, args.get(0), Integer.parseInt(args.get(1)), category);
            }
            case "list" -> {
                return new ListItemsCommand(service);
            }
            case "remove" -> {
                if (args.isEmpty()) throw new IllegalArgumentException("Arguments manquants pour la commande remove");
                return new RemoveItemCommand(service, args.getFirst());
            }
            default -> throw new IllegalArgumentException("Commande inconnue: " + command);
        }
    }
}