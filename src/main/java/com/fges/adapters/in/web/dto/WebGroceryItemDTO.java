package com.fges.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebGroceryItemDTO {
    private String name;
    private int quantity;
    private String category;
}
