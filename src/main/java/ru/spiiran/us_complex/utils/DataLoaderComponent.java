package ru.spiiran.us_complex.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.spiiran.us_complex.repositories.StatusGeneralRepository;
import ru.spiiran.us_complex.repositories.modelsat.msListParamRepository;
import ru.spiiran.us_complex.repositories.modelsat.msModeRepository;

@Component
public class DataLoaderComponent implements CommandLineRunner {

    @Autowired private StatusGeneralRepository statusGeneralRepository;
    @Autowired private msListParamRepository listParamRepository;
    @Autowired private msModeRepository modeRepository;

    @Override
    public void run(String... args) throws Exception {
        //заполнить все необходимые данные
        // в два последний репозитория - из файла config в директории проекта.

    }
}
