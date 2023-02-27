package org.perscholas.cmsapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@ControllerAdvice
@Slf4j
public class MyControllerAdvice {

    @Autowired
    StudentsRepoI studentsRepoI;

    @ExceptionHandler({Exception.class,AccessDeniedException.class})
    public RedirectView exceptionHandle(Exception ex){

      log.debug("something happened");
      ex.printStackTrace();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/");
      return redirectView;
    }

    @ModelAttribute
    public void theStudent(Model model, HttpServletRequest request, HttpSession http){
        Principal p = request.getUserPrincipal();
        Students ss = null;
        if(p != null){
            ss =  studentsRepoI.findByEmail(p.getName()).get();
            http.setAttribute("theStudent", ss);
            log.warn("session attr theStudent in advice controller  " + http.getAttribute("theStudent").toString());

        }
        model.addAttribute("theStudent", ss);
    }


}
