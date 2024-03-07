package ru.spiiran.us_complex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;


@Repository
public interface ConstellationArbitraryRepository extends JpaRepository<coArbitraryConstruction, Long> {
}
