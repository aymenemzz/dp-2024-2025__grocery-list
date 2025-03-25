package com.fges.ServiceImpl;

import com.fges.DAO.GroceryDAO;
import com.fges.Service.GroceryListService;
import com.fges.ValueObject.Item;

public class GroceryListServiceImpl implements GroceryListService {

    private final GroceryDAO groceryDAO;

    public GroceryListServiceImpl(GroceryDAO groceryDAO) {
        this.groceryDAO = groceryDAO;
    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public void deleteItem(Item item) {

    }
}
