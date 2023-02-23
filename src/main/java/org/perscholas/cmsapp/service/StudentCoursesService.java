package org.perscholas.cmsapp.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.dto.StudentDTO;
import org.perscholas.cmsapp.exceptions.MyExceptions;
import org.perscholas.cmsapp.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = {SQLException.class, DataAccessException.class})
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class StudentCoursesService {

    StudentsRepoI studentsRepoI;
    CoursesRepoI coursesRepoI;

    @Autowired
    public StudentCoursesService(StudentsRepoI studentsRepoI, CoursesRepoI coursesRepoI) {
        this.studentsRepoI = studentsRepoI;
        this.coursesRepoI = coursesRepoI;
    }

    public List<StudentDTO> getAllStudents() throws MyExceptions {
        List<StudentDTO> studentsList = studentsRepoI.findAll()
                .stream()
                .map((s)-> new StudentDTO(s.getId(),s.getName(),s.getEmail()))
                .collect(Collectors.toList());
        if (studentsList.size()< 1) throw new MyExceptions("testing out the controller advice");
        log.debug(studentsList.toString());
        return studentsList;
    }


}
