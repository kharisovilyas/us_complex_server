package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.constellation.dtoPlanarConstellation;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.services.constellation.ConstellationService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/constellation")
public class RestConstellationController {
    @Autowired
    private ConstellationService constellationService;

    @PostMapping("/calc/planar")
    private ResponseEntity<dtoMessage> calculatePlanarConstellation(@RequestBody dtoPlanarConstellation planarConstellation){
        return ResponseEntity.ok().body(constellationService.calculationPlanarConstellation(planarConstellation));
    }

    @PostMapping("/update")
    public ResponseEntity<dtoMessage> updateConstellation(@RequestBody dtoConstellation constellation){
        return ResponseEntity.ok().body(constellationService.updateConstellation(constellation));
    }

    @GetMapping("/get/list")
    public ResponseEntity<List<dtoConstellation>> getAllConstellations() {
        return ResponseEntity.ok().body(constellationService.getAllConstellations());
    }

    @PostMapping("/delete/byId")
    public ResponseEntity<dtoMessage> deleteConstellation(@RequestParam Long id){
        return ResponseEntity.ok().body(constellationService.deleteConstellation(id));
    }

    @PostMapping("/set/status")
    public ResponseEntity<dtoMessage> setStatusOfEdit(@RequestParam Boolean status) {
        return ResponseEntity.ok().body(constellationService.setSystemParams(status));
    }

}
