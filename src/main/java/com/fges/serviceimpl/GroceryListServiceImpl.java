package com.fges.serviceimpl;

import com.fges.service.GroceryListService;
import com.fges.storage.dao.GroceryDAO;
import com.fges.valueobject.GroceryItem;
import com.fges.valueobject.GroceryList;

import java.util.List;

public class GroceryListServiceImpl implements GroceryListService {

    private final GroceryDAO groceryDAO;

    public GroceryListServiceImpl(GroceryDAO groceryDAO) {
        this.groceryDAO = groceryDAO;
    }

    @Override
    public void addItem(GroceryItem groceryItem) {
        if (groceryItem == null || groceryItem.getName().isBlank()) {
            throw new IllegalArgumentException("Impossible d'ajouter un item nul ou avec un nom vide !");
        }
        try {
            groceryDAO.addItem(groceryItem);
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'appeler le groceryDAO pour ajouter un item " + e, e);
        }
    }

    @Override
    public void deleteItem(GroceryItem groceryItem) {
        if (groceryItem == null) {
            throw new IllegalArgumentException("Impossible de supprimer un item nul !");
        }
        try {

            groceryDAO.deleteItem(groceryItem);
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'appeler le groceryDAO pour supprimer un item", e);
        }
    }

    @Override
    public GroceryList getAllItems() {
        try {
            return groceryDAO.loadAllItem();
        } catch (Exception e) {
            throw new RuntimeException("Impossible de récupérer la liste des items depuis le groceryDAO : " + e, e);
        }
    }
}