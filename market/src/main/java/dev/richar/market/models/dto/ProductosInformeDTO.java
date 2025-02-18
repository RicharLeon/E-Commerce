package dev.richar.market.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductosInformeDTO {

    private String name;
    private Double price;
    private Boolean status;
    private Integer stock;

}
