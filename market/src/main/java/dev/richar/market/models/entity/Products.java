package dev.richar.market.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.util.Lazy;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "id_category")
    private Integer idCategory;
    @Column(name = "price")
    private Double price;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "status")
    private Boolean status;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "photo", columnDefinition = "MEDIUMBLOB")
    private byte[] photo;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "description")
    private String description;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", insertable = false, updatable = false)
    private Category category;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
