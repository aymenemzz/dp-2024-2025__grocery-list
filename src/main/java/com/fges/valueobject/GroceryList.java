package com.fges.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroceryList {
    private List<GroceryItem> groceryItemList;

    public void addToList(GroceryItem groceryItem)
    {
        this.groceryItemList.add(groceryItem);
    }

    @Override
    public String toString() {
        return "GroceryList{" + groceryItemList.stream()
                                                    .map(GroceryItem::toString)
                                                    .reduce((a, b) -> a + ", " + b)
                                                    .orElse("") +
                '}';
    }
}
