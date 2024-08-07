package vn.com.itechcorp.ris;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.module.modality.ModalityProxy;
import vn.com.itechcorp.module.modality.dto.ModalityDTOUpdate;
import vn.com.itechcorp.module.modality.dto.ModalityDtoCreate;

@RestController
@RequestMapping("/api/ws/ris")
public class RisController {

    @Autowired
    private ModalityProxy modalityProxy;

    @PostMapping("/modality")
    public void makeModality(@RequestParam Long modalityId,
            @RequestBody ModalityDtoCreate modalityDtoCreate) {
        modalityProxy.createModality(modalityId, modalityDtoCreate);
    }

    @PutMapping("/modality")
    public ResponseEntity<String> lockModality(Authentication authentication, @RequestBody ModalityDTOUpdate modalityDtoUpdate) {
        modalityProxy.updateModality(modalityDtoUpdate);
        return ResponseEntity.ok("OK");
    }



}
