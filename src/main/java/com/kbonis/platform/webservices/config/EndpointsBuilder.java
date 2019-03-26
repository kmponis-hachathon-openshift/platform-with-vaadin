package com.kbonis.platform.webservices.config;

import lombok.Data;

@Data
public class EndpointsBuilder {

    public static final String STUDENT_ENDPOINT = "http://localhost:9092/student";

    public static final String STUDENT_FIND_BY_STUDENT_ID_ENDPOINT = STUDENT_ENDPOINT + "/findByStudentId/";

    public static final String STUDENT_FIND_BY_LAST_NAME_ENDPOINT = STUDENT_ENDPOINT + "/findByLastName/";

    public static final String STUDENT_FIND_ALL_ENDPOINT = STUDENT_ENDPOINT + "/findAll";

}
