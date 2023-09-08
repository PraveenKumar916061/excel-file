package com.img.excelfile.controller;

import com.img.excelfile.model.Customer;
import com.img.excelfile.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
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
