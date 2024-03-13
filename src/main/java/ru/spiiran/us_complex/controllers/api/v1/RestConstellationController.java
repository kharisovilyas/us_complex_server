package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.constellation.dtoArbitraryConstruction;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.services.ArbitraryConstructionService;
import ru.spiiran.us_complex.services.ConstellationService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/constellation")
public class RestConstellationController {
    @Autowired
    private ArbitraryConstructionService arbitraryConstructionService;
    @Autowired
    private ConstellationService constellationService;
    @GetMapping("/arbitrary/get/list")
    public ResponseEntity<List<dtoArbitraryConstruction>> getAllArbitraryConstruction() {
        List<dtoArbitraryConstruction> detailedConstellations = new ArrayList<>();
        for (coArbitraryConstruction detailedConstellation
                : arbitraryConstructionService.getAllDetailedConstellations()
        ) {
            detailedConstellations.add(detailedConstellation.getDto());
        }
        return ResponseEntity.ok().body(detailedConstellations);
    }

    @PostMapping("/arbitrary/add")
    public ResponseEntity<dtoMessage> addArbitraryConstruction(@RequestParam Long id, @RequestBody dtoArbitraryConstruction detailedConstellation) {
        return ResponseEntity.ok().body(arbitraryConstructionService.addConstellationArbitrary(id, detailedConstellation));
    }

    @PostMapping("/arbitrary/update/byId")
    public ResponseEntity<dtoMessage> updateArbitraryConstructionById(@RequestParam Long id, @RequestBody dtoArbitraryConstruction detailedConstellation){
        return ResponseEntity.ok().body(arbitraryConstructionService.updateConstellationArbitraryById(id, detailedConstellation));
    }

    @PostMapping("/arbitrary/update/byList")
    public ResponseEntity<dtoMessage> updateArbitraryConstructionByList(@RequestBody List<dtoArbitraryConstruction> detailedConstellations){
        return ResponseEntity.ok().body(arbitraryConstructionService.updateConstellationArbitraryByList(detailedConstellations));
    }

    @PostMapping("/arbitrary/delete/byId")
    public ResponseEntity<dtoMessage> deleteArbitraryConstructionById(@RequestParam Long id){
        return ResponseEntity.ok().body(arbitraryConstructionService.deleteConstellationArbitraryById(id));
    }

    @GetMapping("/arbitrary/get/byId")
    public ResponseEntity<dtoArbitraryConstruction> getArbitraryConstructionById(@RequestParam Long id) {
        return ResponseEntity.ok().body(arbitraryConstructionService.getConstellationArbitraryById(id));
    }
    @PostMapping("/set/status")
    public ResponseEntity<dtoMessage> setStatusOfEdit(@RequestParam Boolean status){
        return ResponseEntity.ok().body(constellationService.setTableStatusOfEdit(status));
    }

    @PostMapping("/update")
    public ResponseEntity<dtoMessage> updateConstellation(@RequestBody dtoConstellation dtoConstellation, @RequestParam Long id){
        return ResponseEntity.ok().body(constellationService.updateConstellation(dtoConstellation, id));
    }

    @PostMapping("/updateByList")
    public ResponseEntity<dtoMessage> updateConstellationByList(@RequestBody List<dtoConstellation> dtoConstellationList, @RequestParam Long id){
        return ResponseEntity.ok().body(constellationService.updateListConstellation(dtoConstellationList));
    }

    @PostMapping("/add")
    public ResponseEntity<dtoMessage> addConstellation(@RequestBody dtoConstellation dtoConstellation){
        return ResponseEntity.ok().body(constellationService.addConstellation(dtoConstellation));
    }

    @PostMapping("/delete")
    public ResponseEntity<dtoMessage> deleteConstellation(@RequestBody Long id){
        return ResponseEntity.ok().body(constellationService.deleteConstellation(id));
    }

    @GetMapping("/get/list")
    public ResponseEntity<List<dtoConstellation>> getAllConstellations() {
        return ResponseEntity.ok().body(constellationService.getAllConstellations());
    }
}
