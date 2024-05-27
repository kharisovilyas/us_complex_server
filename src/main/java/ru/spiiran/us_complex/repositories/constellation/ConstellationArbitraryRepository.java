package ru.spiiran.us_complex.repositories.constellation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;

import java.util.List;


@Repository
public interface ConstellationArbitraryRepository extends JpaRepository<coArbitraryConstruction, Long> {
    @Query("SELECT c FROM coArbitraryConstruction c WHERE c.idNodeEntity.nodeId > :nodeId ORDER BY c.idNodeEntity.nodeId ASC")
    List<coArbitraryConstruction> findAllByNodeIdGreaterThan(@Param("nodeId") Long nodeId);


    // Метод для получения списка coArbitraryConstruction, отсортированного по nodeId
    @Query("SELECT c FROM coArbitraryConstruction c JOIN c.idNodeEntity n ORDER BY n.nodeId ASC")
    List<coArbitraryConstruction> findAllByOrderByNodeIdAsc();
}
