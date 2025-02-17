package dev.richar.market.models.entity;

import dev.richar.market.enums.StatusPay;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

    @Entity
    @Table(name = "carrito")
    @Data
    public class Carrito {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_carrito")
        private Integer idCarrito;
        @Column(name = "id_usuario")
        private Integer idUsuario;

        @Column(name = "created_at")
        private LocalDateTime createdAt;
        @Basic(fetch = FetchType.LAZY)
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @PrePersist
        private void prePersist() {
            this.createdAt = LocalDateTime.now();
        }
        @PreUpdate
        private void preUpdate() {
            this.updatedAt = LocalDateTime.now();
        }

    }
