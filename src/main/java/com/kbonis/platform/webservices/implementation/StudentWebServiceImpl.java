package com.kbonis.platform.webservices.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbonis.platform.model.Student;
import com.kbonis.platform.webservices.config.EndpointsBuilder;
import com.kbonis.platform.webservices.StudentWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service (value = "studentWebService")
public class StudentWebServiceImpl implements StudentWebService {

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    public StudentWebServiceImpl() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void create(Student student) {
        restTemplate.postForObject(EndpointsBuilder.STUDENT_ENDPOINT, student, Student.class);
    }

    @Override
    public void updated(Student student) {
        restTemplate.put(EndpointsBuilder.STUDENT_ENDPOINT, student, Student.class);
    }

    @Override
    public void delete(String studentId) {
        restTemplate.delete(EndpointsBuilder.STUDENT_ENDPOINT, studentId);
    }

    @Override
    public Student findByStudentId(String studentId) {
        return restTemplate.getForObject(
                EndpointsBuilder.STUDENT_FIND_BY_STUDENT_ID_ENDPOINT + studentId, Student.class);
    }

    @Override
    public List<Student> findByLastName(String lastName) {
        String response = restTemplate.getForObject(
                EndpointsBuilder.STUDENT_FIND_BY_LAST_NAME_ENDPOINT + lastName, String.class);
        List<Student> studentList;
        try {
            studentList = objectMapper.readValue(response,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
        } catch (IOException e) {
            studentList = new ArrayList<>();
            log.error("Error to convert Json string to list of students");
        }
        return studentList;
    }

    @Override
    public List<Student> findAll() {
        String response = restTemplate.getForObject(
                EndpointsBuilder.STUDENT_FIND_ALL_ENDPOINT, String.class);
        List<Student> studentList;
        try {
            studentList = objectMapper.readValue(response,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
        } catch (IOException e) {
            studentList = new ArrayList<>();
            log.error("Error to convert Json string to list of students");
        }
        return studentList;
    }
}
