package com.fiuni.distri.project.fiuni.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
public class SampleController {

    @GetMapping("/sample")
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World");
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/sample-role")
    public ResponseEntity<String>helloWorldRole(){
        return  ResponseEntity.ok("Hello World Admin !");
    }

}
