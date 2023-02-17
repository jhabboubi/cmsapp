package org.perscholas.cmsapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
@SessionAttributes(value = {"msg"})
public class HomeController {

    StudentsRepoI studentsRepoI;
    CoursesRepoI coursesRepoI;

    @Autowired
    public HomeController(StudentsRepoI studentsRepoI, CoursesRepoI coursesRepoI) {
        this.studentsRepoI = studentsRepoI;
        this.coursesRepoI = coursesRepoI;
    }

    @ModelAttribute
    public void initModel(Model model){
        model.addAttribute("msg", "Hello world");
    }

    @GetMapping("/model")
    public String model(Model model, HttpServletRequest httpServletRequest){
        log.warn(model.getAttribute("msg").toString());
        HttpSession session = httpServletRequest.getSession();
        log.warn(session.getId());
        session.setAttribute("msg", "changed in model method!!");



        return "model_page";
    }

    @GetMapping(value = {"/", "index"})
    public String homePage(Model model, HttpServletRequest request){


        log.info("i am in the index controller method");

        List<Students> allStud = studentsRepoI.findAll();

        Students s = allStud.get(allStud.size()-1);

        model.addAttribute("allStu",allStud);
        model.addAttribute("info", s);
        for(Students x: allStud){
            System.out.println(x);
        }
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
 // @Controller
    @ResponseBody
    @GetMapping("/api/getAllStudents")
    public List<Students> getAllStudents(){
        return studentsRepoI.findAll();
    }

    @GetMapping("/getform")
    public String getTheForm(){
        return "form_requestparam";
    }

    @GetMapping("/path/{id}/{ddd}/{dddd}")
    public String getUserWithID(@PathVariable(name = "id") int id){
        log.warn(String.valueOf(id));
        log.warn(studentsRepoI.findById(id).toString());
        return "form_requestparam";
    }

    @PostMapping("/request")
    public String requestParam(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("email") String email){
        log.warn(String.format("my id is %d and my name is %s. email: %s", id, name,email));
        return "form_requestparam";
    }



}
