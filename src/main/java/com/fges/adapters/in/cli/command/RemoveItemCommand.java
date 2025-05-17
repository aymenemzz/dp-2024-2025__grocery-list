package com.fges.adapters.in.cli.command;


import com.fges.application.service.GroceryListServiceImpl;
import com.fges.domain.GroceryItem;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveItemCommand implements Command {
    private final GroceryListServiceImpl service;
    private final String itemName;

    @Override
    public int execute() {
        service.deleteItem(new GroceryItem(itemName, 0, "default"));
        return 0;
    }
}