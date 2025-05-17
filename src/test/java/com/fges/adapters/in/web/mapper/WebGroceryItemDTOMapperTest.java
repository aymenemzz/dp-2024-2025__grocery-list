package com.fges.adapters.in.web.mapper;

import com.fges.adapters.in.web.dto.WebGroceryItemDTO;
import com.fges.domain.GroceryItem;
import fr.anthonyquere.MyGroceryShop.WebGroceryItem;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WebGroceryItemDTOMapperTest {

    @Test
    void should_mapDTOtoWebCorrectly() {
        WebGroceryItemDTO dto = new WebGroceryItemDTO("apple", 3, "fruit");
        WebGroceryItem web = WebGroceryItemDTOMapper.toWeb(dto);

        assertThat(web).isInstanceOf(WebGroceryItem.class);

        assertThat(web.name()).isEqualTo("apple");
        assertThat(web.quantity()).isEqualTo(3);
        assertThat(web.category()).isEqualTo("fruit");
    }

    @Test
    void should_mapWebToDTOCorrectly() {
        WebGroceryItem web = new WebGroceryItem("milk", 1, "dairy");
        WebGroceryItemDTO dto = WebGroceryItemDTOMapper.fromWeb(web);

        assertThat(dto).isInstanceOf(WebGroceryItemDTO.class);

        assertThat(dto.getName()).isEqualTo("milk");
        assertThat(dto.getQuantity()).isEqualTo(1);
        assertThat(dto.getCategory()).isEqualTo("dairy");
    }

    @Test
    void should_mapDTOtoDomainCorrectly() {
        WebGroceryItemDTO dto = new WebGroceryItemDTO("bread", 2, "bakery");
        GroceryItem item = WebGroceryItemDTOMapper.toDomain(dto);

        assertThat(item).isInstanceOf(GroceryItem.class);

        assertThat(item.getName()).isEqualTo("bread");
        assertThat(item.getQuantity()).isEqualTo(2);
        assertThat(item.getCategory()).isEqualTo("bakery");
    }

    @Test
    void should_mapDomainToDTOCorrectly() {
        GroceryItem item = new GroceryItem("cheese", 4, "dairy");
        WebGroceryItemDTO dto = WebGroceryItemDTOMapper.fromDomain(item);

        assertThat(dto).isInstanceOf(WebGroceryItemDTO.class);

        assertThat(dto.getName()).isEqualTo("cheese");
        assertThat(dto.getQuantity()).isEqualTo(4);
        assertThat(dto.getCategory()).isEqualTo("dairy");
    }
}
