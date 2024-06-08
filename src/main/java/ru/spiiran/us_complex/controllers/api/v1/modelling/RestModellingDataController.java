package ru.spiiran.us_complex.controllers.api.v1.modelling;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spiiran.us_complex.services.connect.ModellingDatabaseService;

@Controller
@RequestMapping("/api/v1/modelling/data")
public class RestModellingDataController {
    @Autowired
    private ModellingDatabaseService modellingDatabaseService;
}
