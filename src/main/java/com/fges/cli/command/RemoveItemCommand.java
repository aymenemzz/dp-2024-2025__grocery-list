package com.fges.cli.command;

import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.valueobject.GroceryItem;

public class RemoveItemCommand implements Command {
    private final GroceryListServiceImpl service;
    private final String itemName;

    public RemoveItemCommand(GroceryListServiceImpl service, String itemName) {
        this.service = service;
        this.itemName = itemName;
    }

    @Override
    public int execute() {
        service.deleteItem(new GroceryItem(itemName, 0, "default"));
        return 0;
    }
}