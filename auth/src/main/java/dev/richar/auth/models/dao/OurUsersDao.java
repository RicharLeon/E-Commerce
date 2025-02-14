package dev.richar.auth.models.dao;

import dev.richar.auth.models.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OurUsersDao extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);
    Optional<OurUsers> findById(Integer id);
}
