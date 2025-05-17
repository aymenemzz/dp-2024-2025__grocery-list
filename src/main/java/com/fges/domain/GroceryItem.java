package com.fges.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroceryItem {
    private String name;
    private Integer quantity;
    private String category;

    @Override
    public String toString() {
        return "GroceryItem{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                '}';
    }
}