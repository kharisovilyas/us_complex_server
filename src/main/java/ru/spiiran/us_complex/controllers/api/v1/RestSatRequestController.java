package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spiiran.us_complex.model.dto.general.dtoIdNode;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.satrequest.dtoCatalog;
import ru.spiiran.us_complex.model.dto.satrequest.dtoRequest;
import ru.spiiran.us_complex.services.satrequest.RequestService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/satrequest")
public class RestSatRequestController {
    @Autowired private RequestService requestService;

    @GetMapping("/request/get/all")
    public ResponseEntity<List<dtoRequest>> getAllRequests(){
        return ResponseEntity.ok().body(requestService.getAllRequests());
    }

    @GetMapping("/catalog/get/all")
    public ResponseEntity<List<dtoCatalog>> getAllCatalogs(){
        return ResponseEntity.ok().body(requestService.getAllCatalogs());
    }

    @GetMapping("/idnode/get/all")
    public ResponseEntity<List<dtoIdNode>> getAllIdNode(){
        return ResponseEntity.ok().body(requestService.getAllIdNode());
    }

    @PostMapping("/catalog/update")
    public ResponseEntity<dtoMessage> updateRequestByList(@RequestBody List<dtoCatalog> catalogList){
        return ResponseEntity.ok().body(requestService.updateCatalogByList(catalogList));
    }

    @PostMapping("/request/update")
    public ResponseEntity<dtoMessage> updateCatalogByList(@RequestBody List<dtoRequest> requests){
        return ResponseEntity.ok().body(requestService.updateRequestByList(requests));
    }


}
