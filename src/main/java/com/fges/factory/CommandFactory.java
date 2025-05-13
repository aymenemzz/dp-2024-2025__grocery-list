package com.fges.factory;


import com.fges.adapters.in.cli.command.AddItemCommand;
import com.fges.adapters.in.cli.command.Command;
import com.fges.adapters.in.cli.command.ListItemsCommand;
import com.fges.adapters.in.cli.command.RemoveItemCommand;
import com.fges.application.service.GroceryListServiceImpl;
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
            case "web" -> {
                int port = Integer.parseInt(args.get(0));
                return new com.fges.adapters.in.cli.command.WebCommand(service, port);
            }
            default -> throw new IllegalArgumentException("Commande inconnue: " + command);
        }
    }
}