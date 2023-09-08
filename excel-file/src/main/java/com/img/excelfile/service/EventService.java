package com.img.excelfile.service;

import com.img.excelfile.model.Event;
import com.img.excelfile.repository.EventRepository;
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
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public boolean isStudentFile(MultipartFile file){
        String TYPE="text/csv";
        if(!TYPE.equals(file.getContentType())){
            return false;
        }
        else return true;
    }

    public void eventDataSave(MultipartFile file){
        try {
            List<Event> events=eventToCSv(file.getInputStream());
            eventRepository.saveAll(events);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Event> eventToCSv(InputStream inputStream) {
        List<Event> events=new ArrayList<>();

        try(BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8")); CSVParser csvParser=new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<CSVRecord> csvRecords=csvParser.getRecords();
            for(CSVRecord csvRecord:csvRecords){
                Event event=new Event();
                event.setEvent_name(csvRecord.get("eventname"));
                event.setLocation(csvRecord.get("location"));
                event.setPlayers(Integer.parseInt(csvRecord.get("players")));

                events.add(event);
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        return events;
    }
}
