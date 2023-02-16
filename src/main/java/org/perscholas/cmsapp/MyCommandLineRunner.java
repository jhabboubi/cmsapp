package org.perscholas.cmsapp;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.Course;
import org.perscholas.cmsapp.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {


    StudentsRepoI studentsRepoI;

    CoursesRepoI coursesRepoI;



    @Autowired
    public MyCommandLineRunner(StudentsRepoI studentsRepoI, CoursesRepoI coursesRepoI) {
        this.studentsRepoI = studentsRepoI;
        this.coursesRepoI = coursesRepoI;
    }

    @PostConstruct
    void created(){
        log.warn("=============== My CommandLineRunner Got Created ===============");
    }

    @Override
    public void run(String... args) throws Exception {

        Students student = new Students(123, "Jafer", "Jafer@gmail.com");
        Students student2 = new Students(444, "Mohammed", "Mohammed@gmail.com");
        Students student3 = new Students(555, "Anjana", "Anjana@gmail.com");

        studentsRepoI.save(student);
        studentsRepoI.save(student2);
        studentsRepoI.save(student3);

        Course course = new Course(1, "java", "Jafer");
        Course course2 = new Course(2, "spring", "Kevin");
        Course course3 = new Course(3, "sql", "Tyron");

        coursesRepoI.save(course);
        coursesRepoI.save(course2);
        coursesRepoI.save(course3);

    }
}
