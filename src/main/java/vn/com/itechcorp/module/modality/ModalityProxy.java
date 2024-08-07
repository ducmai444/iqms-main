package vn.com.itechcorp.module.modality;

import feign.Logger;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.module.modality.dto.ModalityDTOUpdate;
import vn.com.itechcorp.module.modality.dto.ModalityDtoCreate;

@FeignClient(name = "modalityProxy",
        url = "${modality.url}",
        configuration = ModalityProxy.Configuration.class)
public interface ModalityProxy {

    @DeleteMapping("/delete")
    Response deleteModality(@RequestParam("modalityId") String modalityId);

    @PostMapping("/modality")
    Response createModality(@RequestParam("modalityId") Long modalityId,
                            @RequestBody ModalityDtoCreate modalityDtoCreate);

    @PutMapping("/async/modality")
    Response updateModality(@RequestBody ModalityDTOUpdate enabled);

    class Configuration {
        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }
}

