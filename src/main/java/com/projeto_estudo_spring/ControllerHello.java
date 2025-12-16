package com.projeto_estudo_spring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class ControllerHello {
    
    @GetMapping("/hello")
    public String hello() {
        return "Olá Spring";
    }
    

}
