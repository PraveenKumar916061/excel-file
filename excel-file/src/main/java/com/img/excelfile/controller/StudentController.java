package com.img.excelfile.controller;

import com.img.excelfile.service.StudentService;
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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value="/fileUpload", method= RequestMethod.POST)
    public ResponseEntity<?> uploadStudentFile(@RequestBody MultipartFile file){
        if(studentService.isStudentFile(file)){
            studentService.fileUpload(file);
            return ResponseEntity.ok(Map.of("message","File uploaded successfully"));
        }
        else return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Pls... upload correct file");
    }

}
