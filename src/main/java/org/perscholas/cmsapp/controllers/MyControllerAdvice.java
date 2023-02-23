package org.perscholas.cmsapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
@Slf4j
public class MyControllerAdvice {


    @ExceptionHandler(Exception.class)
    public RedirectView exceptionHandle(Exception ex){

      log.debug("something happened");
      ex.printStackTrace();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/studentform");
      return redirectView;


    }
}
