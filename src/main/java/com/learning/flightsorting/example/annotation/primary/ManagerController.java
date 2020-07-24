package com.learning.flightsorting.example.annotation.primary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {

    @Autowired
    private Manager manager;

    @GetMapping(value = "/managers")
    public ResponseEntity<String> getManagerName(){
        return new ResponseEntity<String>(manager.getManagerName(), HttpStatus.OK);
    }
}
