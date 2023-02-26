package org.perscholas.cmsapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class MyAdvice {


//    @ExceptionHandler({MyExceptions.class, SQLException.class, DataAccessException.class, Exception.class, SQLSyntaxErrorException.class})
//    public RedirectView allExceptions(MyExceptions ex){
//
//        Map<String,String> map = new LinkedHashMap<>();
//        map.put(LocalDateTime.now().toString(),ex.getMessage());
//        log.debug(map.toString());
//        ex.printStackTrace();
//
//        return new RedirectView("/index");
//    }

    @ModelAttribute
    public void initModel(Model model){
        model.addAttribute("msg","hello world");

    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Model model, MaxUploadSizeExceededException e) {
        model.addAttribute("message", "File is too large!");

        return "upload_form";
    }
}
