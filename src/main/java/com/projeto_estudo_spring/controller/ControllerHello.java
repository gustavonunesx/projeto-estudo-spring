package com.projeto_estudo_spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projeto_estudo_spring.service.helloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class ControllerHello {

    @Autowired
    private helloService service;
    
    @GetMapping("/hello")
    public String hello() {
        return service.sendMsgToController();
    }

    
    

}
