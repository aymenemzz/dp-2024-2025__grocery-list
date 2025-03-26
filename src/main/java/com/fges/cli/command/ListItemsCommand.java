package com.fges.cli.command;

import com.fges.serviceimpl.GroceryListServiceImpl;

public class ListItemsCommand implements Command {
    private final GroceryListServiceImpl service;

    public ListItemsCommand(GroceryListServiceImpl service) {
        this.service = service;
    }

    @Override
    public int execute() {
        service.getAllItems().forEach(item -> System.out.println(item.getName() + ": " + item.getQuantity()));
        return 0;
    }
}