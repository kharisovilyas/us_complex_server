package ru.spiiran.us_complex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spiiran.us_complex.model.entitys.ModelEntity;

public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
}
