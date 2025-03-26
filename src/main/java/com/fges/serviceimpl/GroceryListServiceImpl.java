package com.fges.serviceimpl;

import com.fges.dao.GroceryDAO;
import com.fges.service.GroceryListService;
import com.fges.valueobject.Item;

import java.util.List;

public class GroceryListServiceImpl implements GroceryListService {

    private final GroceryDAO groceryDAO;

    public GroceryListServiceImpl(GroceryDAO groceryDAO) {
        this.groceryDAO = groceryDAO;
    }

    @Override
    public void addItem(Item item) {
        if (item == null || item.getName().isBlank()) {
            throw new IllegalArgumentException("Impossible d'ajouter un item nul ou avec un nom vide !");
        }
        try {
            groceryDAO.addItem(item);
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'appeler le groceryDAO pour ajouter un item", e);
        }
    }

    @Override
    public void deleteItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Impossible de supprimer un item nul !");
        }
        try {

            groceryDAO.deleteItem(item);
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'appeler le groceryDAO pour supprimer un item", e);
        }
    }

    @Override
    public List<Item> getAllItems() {
        try {
            return groceryDAO.loadItems();
        } catch (Exception e) {
            throw new RuntimeException("Impossible de récupérer la liste des items depuis le groceryDAO", e);
        }
    }
}