package com.img.excelfile.controller;

import com.img.excelfile.model.Customer;
import com.img.excelfile.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customerData")
    public ResponseEntity<?> uploadCustomerData(@RequestParam("filename") MultipartFile filename){
        customerService.saveCustomerData(filename);
        return ResponseEntity.ok(Map.of("message","customers data successfully uploaded"));
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<List<Customer>> getCustomers(){
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.FOUND);
    }
}
