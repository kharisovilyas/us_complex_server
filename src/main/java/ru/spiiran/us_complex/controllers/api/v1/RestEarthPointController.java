package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.spiiran.us_complex.model.dto.earth.dtoEarthPoint;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;
import ru.spiiran.us_complex.services.EarthPointService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/earth")
public class RestEarthPointController {
    @Autowired private EarthPointService earthPointService;

    @GetMapping("/get/list")
    public ResponseEntity<List<dtoEarthPoint>> getAllEarthPoints() {
        List<dtoEarthPoint> earthPoints = new ArrayList<>();
        for (EarthPointEntity earthPoint : earthPointService.getAllEarthPoints()) {
            earthPoints.add(earthPoint.getDto());
        }
        return ResponseEntity.ok().body(earthPoints);
    }

    @PostMapping("/add")
    public ResponseEntity<dtoMessage> addEarthPoint(@RequestBody dtoEarthPoint earthPoint) {
        return ResponseEntity.ok().body(earthPointService.addEarthPoint(earthPoint));
    }

    @PostMapping("/update/byId")
    public ResponseEntity<dtoMessage> updateEarthPointById(@RequestBody dtoEarthPoint dtoEarthPoint){
        return ResponseEntity.ok().body(earthPointService.updatePointById(dtoEarthPoint.getID(), dtoEarthPoint));
    }

    @PostMapping("/update/byList")
    public ResponseEntity<dtoMessage> updateList(@RequestBody List<dtoEarthPoint> dtoListOfEarthPoint){
        return ResponseEntity.ok().body(earthPointService.updatePointByList(dtoListOfEarthPoint));
    }

    @PostMapping("delete/byId")
    public ResponseEntity<dtoMessage> deletePointById(@RequestParam Long id){
        return ResponseEntity.ok().body(earthPointService.deletePointById(id));
    }

    @GetMapping("/get/byId")
    public ResponseEntity<dtoEarthPoint> getEarthPointById(@RequestParam Long id) {
        return ResponseEntity.ok().body(earthPointService.getEarthPointById(id));
    }

    @PostMapping("/set/status")
    public ResponseEntity<dtoMessage> setStatusOfEdit(@RequestParam Boolean status){
        return ResponseEntity.ok().body(earthPointService.setTableStatusOfEdit(status));
    }

}
