package dev.richar.market.models.dao;

import dev.richar.market.models.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayDao extends JpaRepository<Pay, Integer> {


}
