package ru.spiiran.us_complex.services.modelsat;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.techparam.dtoTechParameter;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.techparam.msParameterEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.techparam.msTechParamEntity;
import ru.spiiran.us_complex.repositories.modelsat.ModelSatRepository;
import ru.spiiran.us_complex.repositories.modelsat.techparam.msListParamRepository;
import ru.spiiran.us_complex.repositories.modelsat.techparam.msTechParamRepository;
import ru.spiiran.us_complex.model.dto.modelsat.techparam.dtoParameter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TechParameterService {
    @Autowired
    private msTechParamRepository msTechPrmRepository;
    @Autowired
    private msListParamRepository msListParamRepository;
    @Autowired
    private ModelSatRepository modelSatRepository;

    public dtoMessage updateTechPrmValue(dtoTechParameter dtoTechParameter) {
        Optional<msParameterEntity> optionalParameterEntity = msListParamRepository.findById(dtoTechParameter.getPrmId());
        Optional<ModelSatEntity> optionalModelSatEntity = modelSatRepository.findById(dtoTechParameter.getIdModelSat());
        if (optionalParameterEntity.isPresent() && optionalModelSatEntity.isPresent()) {
            return msTechPrmRepository.saveAndFlush(
                    new msTechParamEntity(dtoTechParameter)
            ).getDtoMessage("SUCCESS", "Parameter update success");
        } else {
            return new dtoMessage("ERROR", "");
        }
    }

    public List<dtoParameter> getAllTechPrm() {
        return msListParamRepository
                .findAll()
                .stream()
                .map(dtoParameter::new)
                .collect(Collectors.toList());
    }
}
