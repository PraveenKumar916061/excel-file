package com.img.excelfile.controller;

import com.img.excelfile.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value="/uploadFile",method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
        if(personService.isValidFile(file)){
            personService.saveData(file);
            return  ResponseEntity.status(HttpStatus.CREATED).body("Data inserted");
        }
        else
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Pls... upload correct file");
    }
}
