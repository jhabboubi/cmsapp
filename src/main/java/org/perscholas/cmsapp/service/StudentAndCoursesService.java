package org.perscholas.cmsapp.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dto.StudentDTO;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StudentAndCoursesService {

    StudentsRepoI studentsRepoI;
    CoursesRepoI coursesRepoI;

    @Autowired
    public StudentAndCoursesService(StudentsRepoI studentsRepoI, CoursesRepoI coursesRepoI) {
        this.studentsRepoI = studentsRepoI;
        this.coursesRepoI = coursesRepoI;
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Students> getAllStudents() throws Exception {
        List<Students> s = studentsRepoI.findAll();
        s.removeAll(s);
        if(s.isEmpty()) {

            log.debug("empty list of students!");
            throw new Exception("empty list");
        }
        log.debug(s.toString());
        return s;
    }

    public List<StudentDTO> getStudentsEssInfo(){

        return studentsRepoI
                .findAll()
                .stream()
                .map((oneStudent)-> {
                   return new StudentDTO(oneStudent.getId(),oneStudent.getName(), oneStudent.getEmail());
                })
                .collect(Collectors.toList());
//
//            List<Students> ss = studentsRepoI.findAll();
//            List<StudentDTO> sss = new ArrayList<>();
//        for (int i = 0; i < ss.size(); i++) {
//
//            sss.add(new StudentDTO(ss.get(i).getId(),ss.get(i).getName(),ss.get(i).getEmail()));
//
//
//        }
    }

}
