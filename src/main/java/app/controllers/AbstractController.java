package app.controllers;

import app.core.helper.ApplicationError;
import org.apache.maven.InternalErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.el.PropertyNotFoundException;
import java.io.IOException;

/**
 * Created by grom on 03/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */
@ControllerAdvice
public abstract class AbstractController extends RuntimeException {
    @Autowired
    protected ReloadableResourceBundleMessageSource messageSource;

    protected ModelAndView model = new ModelAndView();

    protected String layout = "index";
    protected String layoutError = "error";

    public AbstractController() {
        model.setViewName(layout);
    }

    protected void setDirectoryLayout(String layout) {
        this.layout = layout;
    }

    protected void setErrorLayout(String layout) {
        this.layoutError = layout;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object noFoundException(NoHandlerFoundException ex) {
        ModelAndView model = new ModelAndView();
        model.setViewName(layoutError);
        model.addObject("error", ex);
        model.addObject("page_name", "404");
        return model;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            RuntimeException.class,
            PropertyNotFoundException.class,
            InternalErrorException.class,
            Exception.class,
            MultipartException.class,
            IOException.class,
            ApplicationError.class
    })
    public Object internalServerException(Exception ex) {
        ModelAndView model = new ModelAndView();
        model.setViewName(layoutError);
        model.addObject("error", ex);
        model.addObject("page_name", "500");
        return model;
    }
}
