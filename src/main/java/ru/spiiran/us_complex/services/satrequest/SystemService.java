package ru.spiiran.us_complex.services.satrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.system.dtoSystem;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
import ru.spiiran.us_complex.repositories.satrequest.SystemRepository;

import java.util.Optional;

@Service
public class SystemService {
    @Autowired
    private SystemRepository systemRepository;

    public dtoSystem getSystem() {
        Optional<SystemEntity> optionalSystemEntity = systemRepository.findById(1L);
        return optionalSystemEntity.map(dtoSystem::new).orElseGet(() -> new dtoSystem(new SystemEntity()));
    }

    public dtoMessage updateSystem(dtoSystem dtoSystem) {
        Optional<SystemEntity> optionalSystemEntity = systemRepository.findById(1L);
        if (optionalSystemEntity.isPresent()) {
            SystemEntity existingSystem = new SystemEntity(dtoSystem);
            existingSystem.setID(1L);
            systemRepository.save(existingSystem);
            return new dtoMessage("SUCCESS", "All system update");
        } else {
            systemRepository.save(new SystemEntity(dtoSystem));
            return new dtoMessage("SUCCESS", "You system created");
        }
    }
}
