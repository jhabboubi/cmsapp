package org.perscholas.cmsapp.controllers;


import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("allStudents", allStud);
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

        return "redirect:/";
    }

    @ModelAttribute("search")
    public String searchInit(){
        return "no email";
    }
    @GetMapping("/search")
    public String showSearch(Model model){
        try {

            if(model.containsAttribute("search"))
                log.warn(model.getAttribute("search").toString());
            else
                log.warn("empty search attribute!");
        } catch (Exception e){
            e.printStackTrace();
        }
        return "search";
    }

    @PostMapping("/search_execute")
    public String showSearch(@RequestParam("search") String email, RedirectAttributes redirectAttributes) {

        try {
            Students s = studentsRepoI.findByEmail(email).orElseThrow();
            log.warn(s.toString());
            log.warn("get method search");
            redirectAttributes.addFlashAttribute("search", s);
        } catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/search";
    }

    @ResponseBody
    @GetMapping("getAllStudents")
    public List<Students> getAllStudents(){
        return studentsRepoI.findAll();
    }




}
