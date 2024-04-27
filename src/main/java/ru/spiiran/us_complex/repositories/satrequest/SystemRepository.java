package ru.spiiran.us_complex.repositories.satrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
@Repository
public interface SystemRepository extends JpaRepository<SystemEntity, Long> {
}
