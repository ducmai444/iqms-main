package vn.com.itechcorp.ris.proxy;

import feign.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.com.itechcorp.ris.dto.BasicAuth;
import vn.com.itechcorp.ris.dto.UserTokenDTOGet;

import javax.validation.Valid;

@FeignClient(name = "authProxy",
        url = "${ris.auth.url}",
        configuration = AuthProxy.Configuration.class)
public interface AuthProxy {

    @PostMapping("/login")
    UserTokenDTOGet  getToken(@Valid @RequestBody BasicAuth credential);

    class Configuration {
        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }

}