package dev.richar.market.models.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductsConsultaDTO {

    private Integer id;
    private String name;
    private Integer idCategory;
    private String nameCategory;
    private Double price;
    private Integer stock;
    private Boolean status;
    private byte[] photo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
