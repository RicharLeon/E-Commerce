package dev.richar.market.models.dao;

import dev.richar.market.models.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OurUsersDao extends JpaRepository<OurUsers, Integer> {

    @Query(value = "SELECT id, frecuent_user FROM ourusers " +
            "WHERE frecuent_user = true " +
            "AND id = :id ", nativeQuery = true)
    List<OurUsers> findFrequentUsers(Integer id);

}
