package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.spiiran.us_complex.model.dto.earth.dtoEarthPoint;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.services.earth.EarthPointService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/earth")
public class RestEarthPointController {
    @Autowired private EarthPointService earthPointService;

    @GetMapping("/get/list")
    public ResponseEntity<List<dtoEarthPoint>> getAllEarthPoints() {
        return ResponseEntity.ok().body(earthPointService.getAllEarthPoints());
    }

    @PostMapping("/update/byList")
    public ResponseEntity<dtoMessage> updateList(@RequestBody List<dtoEarthPoint> dtoListOfEarthPoint){
        return ResponseEntity.ok().body(earthPointService.updatePointByList(dtoListOfEarthPoint));
    }

    @PostMapping("/set/status")
    public ResponseEntity<dtoMessage> setStatusOfEdit(@RequestParam Boolean status){
        return ResponseEntity.ok().body(earthPointService.setSystemParams(status));
    }


}
