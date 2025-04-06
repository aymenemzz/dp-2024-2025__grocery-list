package com.fges.cli.command;

import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.valueobject.Item;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddItemCommand implements Command {
    private final GroceryListServiceImpl service;
    private final String itemName;
    private final int quantity;
    private final String category;


    @Override
    public int execute() {
        service.addItem(new Item(itemName, quantity, category));

        return 0;
    }
}