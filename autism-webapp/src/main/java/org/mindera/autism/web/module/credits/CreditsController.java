package org.mindera.autism.web.module.credits;


import com.mindera.microservice.controller.ApiResponse;
import org.mindera.autism.web.controller.SiteController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/module/credits")
public class CreditsController extends SiteController {

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse module() {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processTemplate("module/credits/credits.ftl", null);
            }
        };
    }
}
