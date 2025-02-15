package dev.richar.market.models.entity;

import dev.richar.market.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Integer id;
    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "description")
    private String description;

    @Column(name = "total")
    private Double total;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_order", nullable = false)
    private StatusOrder statusOrder = StatusOrder.PENDIENTE;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private OurUsers users;


    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}




