package com.img.excelfile.service;

import com.img.excelfile.model.Customer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelFileUpload {


    public static boolean isValidFile(MultipartFile file){
        return Objects.equals(file.getContentType(),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<Customer> getCustomerDataFromExcel(InputStream inputStream){
        List<Customer> customers=new ArrayList<>();

        try {
            XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
            XSSFSheet sheet=workbook.getSheet("Customer");

            int rowIndex=0;
            Iterator<Row> rowIterator= sheet.rowIterator();
            while(rowIterator.hasNext() ){
                Row row=rowIterator.next();
                if(rowIndex==0){
                    rowIndex++;
                    continue;
                }
                Customer customer=new Customer();
                Iterator<Cell> cellIterator=row.iterator();
                int cellIndex=0;
                while(cellIterator.hasNext()){
                    Cell cell=cellIterator.next();
                    switch (cellIndex){
                        case 0->customer.setId((int) cell.getNumericCellValue());
                        case 1->customer.setFirst_name(cell.getStringCellValue());
                        case 2->customer.setLast_name(cell.getStringCellValue());
                        case 3->customer.setAge((int) cell.getNumericCellValue());
                        case 4->customer.setGender(cell.getStringCellValue());
                        case 5->customer.setCountry(cell.getStringCellValue());
                        case 6->customer.setDate(cell.getDateCellValue());
                        default -> {
                          //  break;
                        }
                    }
                    cellIndex++;
                }
                customers.add(customer);
            }

            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
}
