package com.kbonis.platform.webservices;

import com.kbonis.platform.model.Student;

import java.io.IOException;
import java.util.List;

public interface StudentWebService {

    /**
     *
     * @param student Student
     */
    void create(Student student);

    /**
     *
     * @param student Student
     */
    void updated(Student student);

    /**
     *
     * @param studentId String
     */
    void delete(String studentId);

    /**
     *
     * @param studentId String
     * @return Student
     */
    Student findByStudentId(String studentId);


    /**
     *
     * @param lastName String
     * @return List<Student>
     */
    List<Student> findByLastName(String lastName);

    /**
     *
     * @return List<Student>
     */
    List<Student> findAll();

}
