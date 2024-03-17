package ru.spiiran.us_complex.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.repositories.modelsat.ModelSatRepository;

@Service
@Transactional
public class ModelSatService {
    @Autowired private ModelSatRepository modelSatRepository;
}
