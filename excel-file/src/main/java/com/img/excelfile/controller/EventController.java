package com.img.excelfile.controller;

import com.img.excelfile.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
@RequestMapping(value="/uploadEventfile",method = RequestMethod.POST)
    public ResponseEntity<?> uploadStudentFile(@RequestBody MultipartFile file){
        if(eventService.isStudentFile(file)){
            eventService.eventDataSave(file);
            return ResponseEntity.ok(Map.of("message","File uploaded"));
        }
        else return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Pls... enter valid file");
    }
}
