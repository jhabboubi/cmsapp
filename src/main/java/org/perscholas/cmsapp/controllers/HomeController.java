package org.perscholas.cmsapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dto.StudentDTO;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.Students;
import org.perscholas.cmsapp.service.StudentAndCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@SessionAttributes(value = {"msg", "theStudent"})
public class HomeController {

    StudentsRepoI studentsRepoI;
    CoursesRepoI coursesRepoI;
    StudentAndCoursesService studentAndCoursesService;
    @Autowired
    public HomeController(StudentsRepoI studentsRepoI, CoursesRepoI coursesRepoI, StudentAndCoursesService studentAndCoursesService) {
        this.studentsRepoI = studentsRepoI;
        this.coursesRepoI = coursesRepoI;
        this.studentAndCoursesService = studentAndCoursesService;
    }



    @ModelAttribute
    public void initModel(Model model){
        model.addAttribute("msg", "Hello world");
    }

    @GetMapping("/model")
    public String model(Model model, HttpSession http){
        log.warn("the attr of session theStudent in model is " + http.getAttribute("theStudent"));

        return "model_page";
    }

    @GetMapping(value = {"/", "index"})
    public String homePage(Model model, HttpServletRequest request, HttpSession http) throws Exception {
        Students ss = null;
        Principal p = request.getUserPrincipal();

        if(p != null){
           ss =  studentsRepoI.findByEmail(p.getName()).get();
           http.setAttribute("theStudent", ss);
           log.warn("session attr theStudent " + http.getAttribute("theStudent").toString());

        }

        log.info("i am in the index controller method");

        List<Students> allStud = studentAndCoursesService.getAllStudents();
        List<Students> allStud2 = studentsRepoI.findAll(Sort.by("name").descending().and(Sort.by("id").ascending()));

        Students s = allStud.get(allStud.size()-1);

        model.addAttribute("allStu",allStud);
        model.addAttribute("info", s);
        model.addAttribute("theStudent", ss);
        for(Students x: allStud){
            System.out.println(x);
        }
        return "index";
    }

    @GetMapping("/studentform")

    public String studentForm(Model model, Principal principal){
        log.warn(principal.getName());
        Students s = studentsRepoI.findByEmail(principal.getName()).get();
        log.warn(s.toString());
        model.addAttribute("student", new Students());

        log.warn("student form method");
        return "form";
    }



    @PostMapping("/s")
    public String studentProcess(@Valid @ModelAttribute("student") Students students, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
        log.debug(bindingResult.getAllErrors().toString());
            return "form";
        }

        log.warn("student process method" + students);
        log.warn(students.toString());

        studentsRepoI.save(students);
        model.addAttribute("inserted", "Persisted into the database!");
        log.debug("this is from model: " + students);


        return "/form";
    }
 // @Controller
    @PreAuthorize("hasAuthority('DELETE')")
    @ResponseBody
    @GetMapping("/api/getAllStudents")
    public List<StudentDTO> getAllStudents(){

        return studentAndCoursesService.getStudentsEssInfo();
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


    @GetMapping("/login")
    public String login(){
        return "login_page";
    }

    @GetMapping("/403")
    public String access(){
        return "403";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }



}
