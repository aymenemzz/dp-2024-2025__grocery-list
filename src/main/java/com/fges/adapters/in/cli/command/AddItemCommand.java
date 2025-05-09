package com.fges.adapters.in.cli.command;

import com.fges.application.service.GroceryListServiceImpl;
import com.fges.domain.GroceryItem;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddItemCommand implements Command {
    private final GroceryListServiceImpl service;
    private final String itemName;
    private final int quantity;
    private final String category;


    @Override
    public int execute() {

        service.addItem(new GroceryItem(itemName, quantity, category));

        return 0;
    }
}