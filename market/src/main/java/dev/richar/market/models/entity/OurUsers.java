package dev.richar.market.models.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ListIndexBase;
import org.springframework.context.annotation.Lazy;

@Entity
@Table(name = "ourusers")
public class OurUsers {
    @Id
    private Integer id;
    @Column(name = "frecuent_user")
    private Boolean frecuentUser;

}
