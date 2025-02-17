package dev.richar.market.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CarritoItemsConsultaDTO {

    private Integer idProduct;
    private Integer idCarritoItem;
    private Integer cantidad;
    private Integer idCategory;
    private String name;
    private byte[] photo;
    private Double price;
    private Boolean status;
    private Integer stock;
    private String description;

}
