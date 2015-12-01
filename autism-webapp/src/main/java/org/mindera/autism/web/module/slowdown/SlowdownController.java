package org.mindera.autism.web.module.slowdown;


import com.google.common.annotations.VisibleForTesting;
import com.mindera.microservice.controller.ApiResponse;
import org.mindera.autism.web.controller.SiteController;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/module/slowdown")
@VisibleForTesting
public class SlowdownController extends SiteController {

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse module() {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                Thread.sleep(5000);
                return getResponseEntity("SLOW");
            }
        };
    }
}
