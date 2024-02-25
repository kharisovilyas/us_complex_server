package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellationArbitrary;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.ConstellationArbitrary;
import ru.spiiran.us_complex.services.ConstellationArbitraryService;
import ru.spiiran.us_complex.services.ConstellationService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/constellation")
public class RestConstellationController {
    @Autowired
    private ConstellationArbitraryService constellationArbitraryService;
    @Autowired
    private ConstellationService constellationService;
    @GetMapping("/arbitrary/get/list")
    public ResponseEntity<List<dtoConstellationArbitrary>> getAllConstellationArbitrary() {
        List<dtoConstellationArbitrary> detailedConstellations = new ArrayList<>();
        for (ConstellationArbitrary detailedConstellation
                : constellationArbitraryService.getAllDetailedConstellations()
        ) {
            detailedConstellations.add(detailedConstellation.getDto());
        }
        return ResponseEntity.ok().body(detailedConstellations);
    }

    @PostMapping("/arbitrary/add")
    public ResponseEntity<dtoMessage> addConstellationArbitrary(@RequestParam Long id, @RequestBody dtoConstellationArbitrary detailedConstellation) {
        return ResponseEntity.ok().body(constellationArbitraryService.addConstellationArbitrary(id, detailedConstellation));
    }

    @PostMapping("/arbitrary/update/byId")
    public ResponseEntity<dtoMessage> updateConstellationArbitraryById(@RequestParam Long id, @RequestBody dtoConstellationArbitrary detailedConstellation){
        return ResponseEntity.ok().body(constellationArbitraryService.updateConstellationArbitraryById(id, detailedConstellation));
    }

    @PostMapping("/arbitrary/update/byList")
    public ResponseEntity<dtoMessage> updateConstellationArbitraryByList(@RequestBody List<dtoConstellationArbitrary> detailedConstellations){
        return ResponseEntity.ok().body(constellationArbitraryService.updateConstellationArbitraryByList(detailedConstellations));
    }

    @PostMapping("/arbitrary/delete/byId")
    public ResponseEntity<dtoMessage> deleteConstellationArbitraryById(@RequestParam Long id){
        return ResponseEntity.ok().body(constellationArbitraryService.deleteConstellationArbitraryById(id));
    }

    @GetMapping("/arbitrary/get/byId")
    public ResponseEntity<dtoConstellationArbitrary> getConstellationArbitraryById(@RequestParam Long id) {
        return ResponseEntity.ok().body(constellationArbitraryService.getConstellationArbitraryById(id));
    }
    @PostMapping("/set/status")
    public ResponseEntity<dtoMessage> setStatusOfEdit(@RequestParam Boolean status){
        return ResponseEntity.ok().body(constellationService.setTableStatusOfEdit(status));
    }

    @PostMapping("/update/byList")
    public ResponseEntity<dtoMessage> updateConstellation(@RequestBody dtoConstellation dtoConstellation, @RequestParam Long id){
        return ResponseEntity.ok().body(constellationService.updateConstellation(dtoConstellation, id));
    }

    @PostMapping("/add/byList")
    public ResponseEntity<dtoMessage> addConstellation(@RequestBody dtoConstellation dtoConstellation){
        return ResponseEntity.ok().body(constellationService.addConstellation(dtoConstellation));
    }
    @GetMapping("/get/list")
    public ResponseEntity<List<dtoConstellation>> getAllConstellations() {
        return ResponseEntity.ok().body(constellationService.getAllConstellations());
    }
}
