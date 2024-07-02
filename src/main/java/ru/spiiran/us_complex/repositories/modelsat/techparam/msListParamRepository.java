package ru.spiiran.us_complex.repositories.modelsat.techparam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.modelsat.old.techparam.msParameterEntity;

@Repository
public interface msListParamRepository extends JpaRepository<msParameterEntity, Long> {
}
