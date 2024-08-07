package vn.com.itechcorp.ris.proxy;

import feign.Logger;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.ris.dto.DiagnosisStepStatus;
import vn.com.itechcorp.ris.dto.OrderDTOGet;
import vn.com.itechcorp.ris.dto.OrderFilter;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@FeignClient(name = "risProxy",
        url = "${ris.url:27.72.147.196:28001/api/secured/ws/rest/v1/async/hospital/31313}",
        configuration = RisProxy.Configuration.class)
public interface RisProxy {

    @GetMapping("/me")
    Response getMyUserInfo(@RequestHeader(value = "Authorization") String authorization);

    @PostMapping("/search/order")
    APIListResponse<List<OrderDTOGet>> searchOrder(@RequestHeader(value = "Authorization") String authorization,
                                                   @Valid @RequestBody OrderFilter entity);

    @GetMapping("/list-order/{ids}")
    APIListResponse<List<OrderDTOGet>> getOrders(@RequestHeader(value = "Authorization") String authorization,
                                                 @PathVariable("ids") Collection<Long> ids);

    @PutMapping("/order/{orderId}")
    Response updateOrder(@Valid @PathVariable String orderId, @RequestBody DiagnosisStepStatus status);

    class Configuration {
        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }

}
