package com.saa.loggingWithAOP.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @GetMapping("/log-example")
    public String logExample(@RequestParam(value = "param", defaultValue = "") String param) {
//        log.info("Handling request with param: {}", param);
        return "Request handled "+param;
    }

    @GetMapping("/log-example-exception")
    public String logExampleException(@RequestParam(value = "param", defaultValue = "") String param) throws Exception {
//        log.info("Handling request with param: {}", param);
            throw new Exception("No Exception", new Throwable("Test for AfterThrowing aspect"));
    }
}
