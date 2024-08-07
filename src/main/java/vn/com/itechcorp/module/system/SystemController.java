package vn.com.itechcorp.module.system;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "system-api", tags = "system-api")
@RequestMapping("/qms/ws/rest/v1")
public class SystemController {

    @Autowired
    private SystemMethod systemMethod;

    @GetMapping("/currentTime")
    public String getCurrentTime(){
        return systemMethod.getCurrentTime();
    }
}
