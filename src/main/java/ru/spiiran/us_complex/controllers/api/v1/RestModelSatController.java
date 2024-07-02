package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.dtoModelSat;
import ru.spiiran.us_complex.model.dto.modelsat.old.equipment.dtoEquipment;
import ru.spiiran.us_complex.model.dto.modelsat.old.equipment.dtoEquipmentType;
import ru.spiiran.us_complex.model.dto.modelsat.old.power.dtoPowerTable;
import ru.spiiran.us_complex.model.dto.modelsat.old.techparam.dtoParameter;
import ru.spiiran.us_complex.model.dto.modelsat.old.techparam.dtoTechParameter;
import ru.spiiran.us_complex.services.modelsat.PowerService;
import ru.spiiran.us_complex.services.modelsat.EquipmentService;
import ru.spiiran.us_complex.services.modelsat.ModelSatService;
import ru.spiiran.us_complex.services.modelsat.TechParameterService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/modelsat")
public class RestModelSatController {
    @Autowired private ModelSatService modelSatService;
    @Autowired private TechParameterService techParameterService;
    @Autowired private EquipmentService equipmentService;
    @Autowired private PowerService powerService;
    @PostMapping("/add")
    public ResponseEntity<dtoMessage> addModeSat(@RequestBody dtoModelSat dtoModelSat){
        return ResponseEntity.ok().body(modelSatService.addModelSat(dtoModelSat));
    }

    @PostMapping("/image/upload")
    public ResponseEntity<dtoMessage> uploadImage(@RequestParam Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(modelSatService.uploadImageOfModelSat(id, file));
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam String imageName) {
        return modelSatService.getImageOfModelSat(imageName);
    }

    @PostMapping("/update")
    public ResponseEntity<dtoMessage> updateModelSat(@RequestBody dtoModelSat dtoModelSat){
        return ResponseEntity.ok().body(modelSatService.updateModelSat(dtoModelSat));
    }

    @PostMapping("/delete")
    public ResponseEntity<dtoMessage> deleteModelSat(@RequestParam Long id){
        return ResponseEntity.ok().body(modelSatService.deleteModelSat(id));
    }

    @PostMapping("/add/techparam")
    public ResponseEntity<dtoMessage> addTechParamValue(@RequestBody dtoTechParameter dtoTechParameter){
        return ResponseEntity.ok().body(techParameterService.updateTechPrmValue(dtoTechParameter));
    }

    @GetMapping("/all/modelsat")
    public ResponseEntity<List<dtoModelSat>> getAllModelSat(){
        return ResponseEntity.ok().body(modelSatService.getAllModelSat());
    }

    @GetMapping("/all/techparam")
    public ResponseEntity<List<dtoParameter>> getAllTechParameter(){
        return ResponseEntity.ok().body(techParameterService.getAllTechPrm());
    }

    @GetMapping("/all/equipment/types")
    public ResponseEntity<List<dtoEquipmentType>> getEquipmentTypes(){
        return ResponseEntity.ok().body(equipmentService.getAllEquipmentTypes());
    }

    @PostMapping("/update/eqipment")
    public ResponseEntity<dtoMessage> updateEquipment(@RequestBody dtoEquipment dto){
        return ResponseEntity.ok().body(equipmentService.addAndUpdateEquipment(dto));
    }

    @GetMapping("/all/power")
    public ResponseEntity<dtoPowerTable> getAllDataForTable(){
        return ResponseEntity.ok().body(powerService.getAllDataForTable());
    }

}
