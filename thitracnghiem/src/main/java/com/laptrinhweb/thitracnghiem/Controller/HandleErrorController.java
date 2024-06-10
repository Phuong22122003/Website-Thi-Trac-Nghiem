package com.laptrinhweb.thitracnghiem.Controller;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
// @EnableWebMvc
public class HandleErrorController {
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Page not found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        mav.setViewName("error/403");
        return mav;
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handlerAccessDeniedExceptin(AccessDeniedException ex){
        ModelAndView mav = new ModelAndView();
        mav.addObject("error","Your access is denied");
        mav.setStatus(HttpStatus.NOT_ACCEPTABLE);
        mav.setViewName("error/405");
        return mav;
    }
}
