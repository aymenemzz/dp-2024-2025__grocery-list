package com.fges.cli.command;

import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.valueobject.Item;

public class RemoveItemCommand implements Command {
    private final GroceryListServiceImpl service;
    private final String itemName;

    public RemoveItemCommand(GroceryListServiceImpl service, String itemName) {
        this.service = service;
        this.itemName = itemName;
    }

    @Override
    public int execute() {
        service.deleteItem(new Item(itemName, 0));
        return 0;
    }
}