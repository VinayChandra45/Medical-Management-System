package com.nrifintech.medico.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
  public static final String DEFAULT_ERROR_VIEW = "error";

  @ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
  
  @ExceptionHandler(ConstraintViolationException.class)
  public ModelAndView constraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException exception) {
      List<String> errorMessages = exception.getConstraintViolations().stream()
              .map(ConstraintViolation::getMessage)
              .collect(Collectors.toList());
      
      ModelAndView mav = new ModelAndView();
		mav.addObject("errors", errorMessages);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
  }
  
  @ExceptionHandler(BindException.class)
  public ModelAndView bindExceptionHandler(HttpServletRequest req, BindException exception) {
      List<String> errorMessages = exception.getAllErrors().stream()
              .map(ObjectError::getDefaultMessage)
              .collect(Collectors.toList());
      
      ModelAndView mav = new ModelAndView();
		mav.addObject("errors", errorMessages);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
  }
}
