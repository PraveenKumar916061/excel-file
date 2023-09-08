package com.img.excelfile.service;

import com.img.excelfile.model.Student;
import com.img.excelfile.repository.StudentRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public boolean isStudentFile(MultipartFile file){
        String type="text/csv";
        if(!type.equals(file.getContentType())){
            return false;
        }
        else return true;
    }

    public void fileUpload(MultipartFile file){
        try {
            List<Student> students=studentToCsv(file.getInputStream());
            studentRepository.saveAll(students);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Student> studentToCsv(InputStream inputStream) {
        List<Student> students =new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); CSVParser csvParser=new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<CSVRecord> csvRecords=csvParser.getRecords();

            for(CSVRecord csvRecord:csvRecords){
                Student student=new Student();
                student.setSt_name(csvRecord.get("stname"));
                student.setSub1(Integer.parseInt(csvRecord.get("sub1")));
                student.setSub2(Integer.parseInt(csvRecord.get("sub2")));
                student.setSub3(Integer.parseInt(csvRecord.get("sub3")));
                student.setTotal(Integer.parseInt(csvRecord.get("sub1"))+Integer.parseInt(csvRecord.get("sub2"))+Integer.parseInt(csvRecord.get("sub3")));

                students.add(student);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}
