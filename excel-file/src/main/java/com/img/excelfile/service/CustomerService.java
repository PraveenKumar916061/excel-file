package com.img.excelfile.service;

import com.img.excelfile.model.Customer;
import com.img.excelfile.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void saveCustomerData(MultipartFile file){
        if(ExcelFileUpload.isValidFile(file)){
            try {
                List<Customer> customers=ExcelFileUpload.getCustomerDatFromExcel(file.getInputStream());
                customerRepository.saveAll(customers);
            } catch (IOException e) {
                throw new RuntimeException("Invalid file");
            }
        }
    }

    public List<Customer> getCustomers(){
       return customerRepository.findAll();
    }
}
