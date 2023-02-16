package org.perscholas.cmsapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class HomeController {

    StudentsRepoI studentsRepoI;
    CoursesRepoI coursesRepoI;

    @Autowired
    public HomeController(StudentsRepoI studentsRepoI, CoursesRepoI coursesRepoI) {
        this.studentsRepoI = studentsRepoI;
        this.coursesRepoI = coursesRepoI;
    }

    @GetMapping(value = {"/", "index"})
    public String homePage(Model model){
        log.info("i am in the index controller method");

        List<Students> allStud = studentsRepoI.findAll();

        Students s = allStud.get(allStud.size()-1);


        model.addAttribute("info", s);
        return "index";
    }

    @GetMapping("/studentform")
    public String studentForm(Model model){
        model.addAttribute("student", new Students());
        log.warn("student form method");
        return "form";
    }

    @PostMapping("/s")
    public String studentProcess(@ModelAttribute("student") Students students){


        log.warn("student process method" + students);
        log.warn(students.toString());
        studentsRepoI.save(students);

        return "form";
    }



}
