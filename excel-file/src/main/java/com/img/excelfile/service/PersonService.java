package com.img.excelfile.service;

import com.img.excelfile.model.Person;
import com.img.excelfile.repository.PersonRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public boolean isValidFile(MultipartFile file){
         String type="text/csv";
        if(!type.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public void saveData(MultipartFile file){
        try {
            List<Person> persons_ =csvToPersons(file.getInputStream());
            personRepository.saveAll(persons_);
        } catch (IOException e) {
            throw new RuntimeException("Invalid file");
        }
    }

    private List<Person> csvToPersons(InputStream inputStream) {
        List<Person> persons=new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)); CSVParser csvParser=new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())){

            List<CSVRecord> csvRecords=csvParser.getRecords();
            for(CSVRecord csvRecord:csvRecords){
                Person person=new Person();
                person.setGender(csvRecord.get("gender"));
                person.setName(csvRecord.get("name"));
                person.setAge(Integer.parseInt(csvRecord.get("age")));
                persons.add(person);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

}
