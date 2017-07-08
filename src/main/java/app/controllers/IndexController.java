package app.controllers;

import org.apache.log4j.Logger;
import org.apache.maven.InternalErrorException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by grom on 03/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */
@Controller
public class IndexController extends AbstractController {
    private static final Logger logger = Logger.getLogger(IndexController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(HttpSession httpSession, HttpServletRequest request) throws InternalErrorException {
        model.setViewName(layout);
        model.addObject("title", "");
        return model;
    }

    @RequestMapping(value = {"{path:(?!resources|error).*$}", "{path:(?!resources|error).*$}/**"}, method = RequestMethod.GET)
    public ModelAndView eshop(HttpSession httpSession, HttpServletRequest request) throws InternalErrorException {
        model.setViewName(layout);
        model.addObject("title", "");
        return model;
    }
}
