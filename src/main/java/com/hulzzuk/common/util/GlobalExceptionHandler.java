package com.hulzzuk.common.util;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("exceptionType", ExceptionHelper.getExceptionType(ex));
        model.addAttribute("exceptionMessage", ExceptionHelper.getExceptionMessage(ex));
        return "common/Exception";
    }
}
