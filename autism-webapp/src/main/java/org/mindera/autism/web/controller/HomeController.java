package org.mindera.autism.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        model.put("message", "Let's overcome Autism together!");
        return "home";
    }
}
