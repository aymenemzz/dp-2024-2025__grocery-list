package com.fges.dao;

import com.fges.storage.strategy.StorageStrategy;
import com.fges.valueobject.Item;

import java.util.List;

public class GroceryDAO {
    private final StorageStrategy storageStrategy;

    public GroceryDAO(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public void addItem(Item item) {
        try {
            storageStrategy.addItem(item);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'item : " + e.getMessage());
        }
    }

    public void addItemList(List<Item> itemList) {
        try {
            storageStrategy.addItemList(itemList);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de la liste d'items : " + e.getMessage());
        }
    }

    public List<Item> loadItems() {
        try {
            return storageStrategy.loadAllItem();
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des items : " + e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }

    public void deleteItem(Item item) {
        try {
            storageStrategy.deleteItem(item);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'item : " + e.getMessage());
        }
    }
}