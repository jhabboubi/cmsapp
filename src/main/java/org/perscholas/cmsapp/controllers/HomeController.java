package org.perscholas.cmsapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.dto.StudentDTO;
import org.perscholas.cmsapp.exceptions.MyExceptions;
import org.perscholas.cmsapp.models.Students;
import org.perscholas.cmsapp.service.StudentCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

@Controller
@Slf4j
@SessionAttributes(value = {"msg", "newUser"})
public class HomeController {

    StudentsRepoI studentsRepoI;
    CoursesRepoI coursesRepoI;
    StudentCoursesService studentCoursesService;

    @Autowired
    public HomeController(StudentsRepoI studentsRepoI, CoursesRepoI coursesRepoI,StudentCoursesService studentCoursesService) {
        this.studentsRepoI = studentsRepoI;
        this.coursesRepoI = coursesRepoI;
        this.studentCoursesService = studentCoursesService;
    }



    @GetMapping("/model")
    public String model(Principal principal,HttpSession http, Model model, @ModelAttribute("msg") String msg){
        log.warn(principal.getName());
        log.warn("first call: " + msg);
        log.warn(http.getId());
        model.addAttribute("msg","hello world 222");
        log.warn("containsAttribute(\"msg\")" + String.valueOf(model.containsAttribute("msg")));
        log.warn((String) model.getAttribute("msg"));



        return "model_page";
    }

    @GetMapping(value = {"/", "index"})
    public String homePage(Model model, HttpServletRequest request){


        log.info("i am in the index controller method");

        List<Students> allStud = studentsRepoI.findAll();
        List<Students> allStud2 = studentsRepoI.findAll(Sort.by("name").descending());
        allStud2.forEach((s)-> log.debug(s.getName() + " | "));

        Students s = allStud.get(allStud.size()-1);


        model.addAttribute("allStu",allStud);
        model.addAttribute("info", s);
        for(Students x: allStud){
            System.out.println(x);
        }
        return "index";
    }

    @GetMapping("/studentform")
    public String studentForm(Principal principal,Model model,@SessionAttribute("msg") String msg, HttpSession http){
        log.warn(principal.getName());
        model.addAttribute("student", new Students());

        return "form";
    }

    @PostMapping("/s")
    public String studentProcess(@Valid @ModelAttribute("student") Students students, BindingResult bindingResult, Principal principal){
        log.warn(principal.getName());
        if(bindingResult.hasErrors()){
            log.error("Student has errors: " + students);
            log.error(bindingResult.getAllErrors().toString());
            return "form";
        }

        students = studentsRepoI.save(students);
        log.warn("student process method" + students);
        log.warn(students.toString());
        log.info("successfully persisted into database");

        return "form";
    }
 // @Controller
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @GetMapping("/api/getAllStudents")
    public List<StudentDTO> getAllStudents(Principal principal) throws MyExceptions {
        log.warn(principal.getName());
        return studentCoursesService.getAllStudents();
    }

    @GetMapping("/getform")
    public String getTheForm(Principal principal,Model model, HttpSession http){
        log.warn(principal.getName());
        log.warn("third call: " + model.getAttribute("msg"));
        log.warn(http.getId());
        model.addAttribute("msg","hello world 444");
        log.warn("containsAttribute(\"msg\")" + String.valueOf(model.containsAttribute("msg")));
        log.warn((String) model.getAttribute("msg"));

        return "form_requestparam";
    }

    @GetMapping("/path/{id}")
    public String getUserWithID(@PathVariable(name = "id") int id, Principal principal){
        log.warn(principal.getName());
        log.warn(String.valueOf(id));
        log.warn(studentsRepoI.findById(id).toString());
        return "form_requestparam";
    }

    @PostMapping("/request")
    public String requestParam(Principal principal,@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("email") String email){
        log.warn(principal.getName());
        log.warn(String.format("my id is %d and my name is %s. email: %s", id, name,email));
        return "form_requestparam";
    }

    @GetMapping("/formjs")
    public String getTheForm(Principal principal){
        log.warn(principal.getName());
        log.warn("form js");
        return "formjs";
    }
//    @PostMapping("/postform")
//    @ResponseBody
//    public ResponseEntity<?> saveForm(@RequestParam int id, @RequestParam String name, @RequestParam String email,@RequestParam String password) throws Exception{
//        Students s = new Students(id, name,email,password);
//        log.warn(s.toString());
//        studentsRepoI.save(s);
//        return ResponseEntity.ok("saved to DB");
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/postform")
    @ResponseBody
    public ResponseEntity<?> saveForm(Principal principal, @ModelAttribute Students s, @RequestParam("file")MultipartFile file) throws Exception{
        //Students s = new Students(id, name,email,password);
        log.warn(file.getOriginalFilename());

        log.warn(s.toString());
        file.transferTo(new File("/Users/habboubi/IdeaProjects/cmsapp/src/main/resources/static/"+file.getOriginalFilename()));
        studentsRepoI.save(s);
        return ResponseEntity.ok("saved to DB");
    }




}
