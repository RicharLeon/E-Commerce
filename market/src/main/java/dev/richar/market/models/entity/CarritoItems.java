package dev.richar.market.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carrito_items")
@Data
public class CarritoItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito_item")
    private Integer idCarritoItem;
    @Column(name = "id_carrito")
    private Integer idCarrito;
    @Column(name = "id_producto")
    private Integer idProducto;
    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito", insertable = false, updatable = false)
    private Carrito carrito;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", insertable = false, updatable = false)
    private Products producto;
}
