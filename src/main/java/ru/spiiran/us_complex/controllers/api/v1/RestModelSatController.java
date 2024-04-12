package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.dtoModelSat;
import ru.spiiran.us_complex.model.dto.modelsat.techparam.dtoTechParameter;
import ru.spiiran.us_complex.services.modelsat.ModelSatService;
import ru.spiiran.us_complex.services.modelsat.TechParameterService;

@Controller
@RequestMapping("/api/v1/modelsat")
public class RestModelSatController {
    @Autowired private ModelSatService modelSatService;
    @Autowired private TechParameterService techParameterService;
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
    public ResponseEntity<dtoMessage> updateModelSat(@RequestParam Long id, @RequestBody dtoModelSat dtoModelSat){
        return ResponseEntity.ok().body(modelSatService.updateModelSat(id, dtoModelSat));
    }

    @PostMapping("/delete")
    public ResponseEntity<dtoMessage> deleteModelSat(@RequestParam Long id){
        return ResponseEntity.ok().body(modelSatService.deleteModelSat(id));
    }

    @PostMapping("/add/techparam")
    public ResponseEntity<dtoMessage> addTechParamValue(@RequestBody dtoTechParameter dtoTechParameter){
        return ResponseEntity.ok().body(techParameterService.updateTechPrmValue(dtoTechParameter));
    }
}
