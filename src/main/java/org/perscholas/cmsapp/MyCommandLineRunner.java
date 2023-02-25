package org.perscholas.cmsapp;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.Course;
import org.perscholas.cmsapp.models.Students;
import org.perscholas.cmsapp.service.FilesStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {


    StudentsRepoI studentsRepoI;

    CoursesRepoI coursesRepoI;
    FilesStorageServiceImpl filesStorageService;


    @Autowired
    public MyCommandLineRunner(StudentsRepoI studentsRepoI, CoursesRepoI coursesRepoI, FilesStorageServiceImpl filesStorageService) {
        this.studentsRepoI = studentsRepoI;
        this.coursesRepoI = coursesRepoI;
        this.filesStorageService = filesStorageService;
    }

    @PostConstruct
    void created(){
        log.warn("=============== My CommandLineRunner Got Created ===============");
    }

    @Async
    @Override
    public void run(String... args) throws Exception {

        try {
            filesStorageService.init();
            log.warn("file init executed!");
            Students student = new Students("Jafer", "Jafer@gmail.com", "password");
            Students student2 = new Students("Mohammed", "Mohammed@gmail.com", "password");
            Students student3 = new Students("Anjana", "Anjana@gmail.com", "password");

            student = studentsRepoI.saveAndFlush(student);
            log.debug(student.toString());
            student2 = studentsRepoI.saveAndFlush(student2);
            log.debug(student2.toString());

            student3 = studentsRepoI.saveAndFlush(student3);
            log.debug(student3.toString());

            Course course = new Course(1412, "java", "Jafer");
            Course course2 = new Course(2312, "spring", "Kevin");
            Course course3 = new Course(3232, "sql", "Tyron");

            course = coursesRepoI.saveAndFlush(course);
            course2 = coursesRepoI.saveAndFlush(course2);
            course3 = coursesRepoI.saveAndFlush(course3);

            student.addCourse(course);
            student.addCourse(course2);

            studentsRepoI.saveAndFlush(student);
            log.debug("s 1 : after courses"+ student.toString());


            student2.addCourse(course3);
            studentsRepoI.saveAndFlush(student2);
            log.debug("s 2 : after courses"+ student2.toString());



//            (new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                        Students ss = studentsRepoI.findByEmail("Anjana@gmail.com").get();
//                        ss.addCourse(coursesRepoI.findById(3232).get());
//                        ss = studentsRepoI.saveAndFlush(ss);
//                        log.warn("s 3 : after courses"+ ss.toString());
//                    } catch (InterruptedException e) {}
//                }
//            })).start();


        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
}
