package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.system.dtoSystem;
import ru.spiiran.us_complex.services.satrequest.SystemService;

@Controller
@RequestMapping("/api/v1/system")
public class RestSystemController {
    @Autowired
    private SystemService systemService;
    @GetMapping("/get")
    public ResponseEntity<dtoSystem> getSystem(){
        return ResponseEntity.ok().body(systemService.getSystem());
    }

    @PostMapping("/update")
    public ResponseEntity<dtoMessage> updateSystem(@RequestBody dtoSystem dtoSystem){
        return ResponseEntity.ok().body(systemService.updateSystem(dtoSystem));
    }
}
