package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.spiiran.us_complex.model.dto.constellation.dtoDetailedConstellation;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.ConstellationDetailed;
import ru.spiiran.us_complex.services.ConstellationDetailedService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/constellation")
public class RestConstellationController {
    @Autowired
    private ConstellationDetailedService constellationDetailedService;
    @GetMapping("/detail/get/list")
    public ResponseEntity<List<dtoDetailedConstellation>> getAllConstellationsDetailed() {
        List<dtoDetailedConstellation> detailedConstellations = new ArrayList<>();
        for (ConstellationDetailed detailedConstellation
                : constellationDetailedService.getAllDetailedConstellations()
        ) {
            detailedConstellations.add(detailedConstellation.getDto());
        }
        return ResponseEntity.ok().body(detailedConstellations);
    }

    @PostMapping("/detail/add")
    public ResponseEntity<dtoMessage> addEarthPoint(@RequestParam Long id, @RequestBody dtoDetailedConstellation detailedConstellation) {
        return ResponseEntity.ok().body(constellationDetailedService.addConstellationDetailed(id, detailedConstellation));
    }

    @PostMapping("/detail/update/byId")
    public ResponseEntity<dtoMessage> updateEarthPointById(@RequestParam Long id, @RequestBody dtoDetailedConstellation detailedConstellation){
        return ResponseEntity.ok().body(constellationDetailedService.updateConstellationDetailedById(id, detailedConstellation));
    }

    @PostMapping("/detail/update/byList")
    public ResponseEntity<dtoMessage> updateList(@RequestBody List<dtoDetailedConstellation> detailedConstellations){
        return ResponseEntity.ok().body(constellationDetailedService.updateConstellationDetailedByList(detailedConstellations));
    }

    @PostMapping("/detail/delete/byId")
    public ResponseEntity<dtoMessage> deletePointById(@RequestParam Long id){
        return ResponseEntity.ok().body(constellationDetailedService.deleteConstellationDetailedById(id));
    }

    @GetMapping("/detail/get/byId")
    public ResponseEntity<dtoDetailedConstellation> getEarthPointById(@RequestParam Long id) {
        return ResponseEntity.ok().body(constellationDetailedService.getConstellationDetailedById(id));
    }
    @PostMapping("/detail/set/status")
    public ResponseEntity<dtoMessage> setStatusOfEdit(@RequestParam Boolean status){
        return ResponseEntity.ok().body(constellationDetailedService.setTableStatusOfEdit(status));
    }
}
