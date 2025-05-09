package com.fges.cli.command;

import com.fges.serviceimpl.GroceryListServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class ListItemsCommand implements Command {
    private final GroceryListServiceImpl service;

    public ListItemsCommand(GroceryListServiceImpl service) {
        this.service = service;
    }

    @Override
    public int execute() {
        Map<String, List<String>> categorizedItems = new HashMap<>();

        service.getAllItems().getGroceryItemList().forEach(item -> {
            String category = item.getCategory();
            String line = "\t" + item.getName() + " : " + item.getQuantity();
            categorizedItems.computeIfAbsent(category, k -> new ArrayList<>()).add(line);
        });

        categorizedItems.forEach((category, items) -> {
            System.out.println(category + " :");
            items.forEach(System.out::println);
            System.err.println("\n");
        });

        return 0;
    }
}