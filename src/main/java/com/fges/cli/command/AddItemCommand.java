package com.fges.cli.command;

import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.valueobject.Item;

public class AddItemCommand implements Command {
    private final GroceryListServiceImpl service;
    private final String itemName;
    private final int quantity;

    public AddItemCommand(GroceryListServiceImpl service, String itemName, int quantity) {
        this.service = service;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    @Override
    public int execute() {
        service.addItem(new Item(itemName, quantity));
        return 0;
    }
}