package app.controllers;

import app.core.helper.ApplicationError;
import app.views.ApiAnswer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.InternalErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.el.PropertyNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by grom on 04/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */

@ControllerAdvice
@RestController
public abstract class AbstractRestController {
    @Autowired
    protected ReloadableResourceBundleMessageSource messageSource;

    protected HashMap properties = new HashMap();
    protected ApiAnswer answer = new ApiAnswer();

    @RequestMapping(value = "*", method = {RequestMethod.GET, RequestMethod.POST})
    public Object general(HttpServletRequest req, HttpServletResponse res) throws NoHandlerFoundException {
        throw new NoHandlerFoundException(req.getMethod(), req.getRequestURI(), new HttpHeaders());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object noFoundException(NoHandlerFoundException ex) {
        answer.setStatus(HttpStatus.NOT_FOUND);
        properties.put("page", ex.getMessage());
        answer.setDetails(properties);
        return answer;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class, PropertyNotFoundException.class, InternalErrorException.class, Exception.class, IOException.class, ApplicationError.class, NullPointerException.class})
    public Object internalServerException(Exception ex) {
        answer.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        properties.put("error", ex.getMessage());
        answer.setDetails(properties);
        return answer;
    }

    /**
     * @param response
     * @param status
     * @param servletStatus
     * @param data
     */
    protected void prepareAnswer(HttpServletResponse response, HttpStatus status, int servletStatus, HashMap data) {
        answer.setStatus(status);
        response.setStatus(servletStatus);
        answer.setDetails(data);
    }

    /**
     * @param request
     * @return
     * @throws IOException
     */
    protected Properties getRequestData(HttpServletRequest request) throws IOException {
        Properties data = new Properties();
        if (request.getContentType() != null && request.getContentType().contains("application/json")) {
            BufferedReader reader = request.getReader();
            String line;
            StringBuilder jsonBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            data = objectMapper.readValue(jsonBuilder.toString(), Properties.class);
        } else {
            Enumeration<String> attributes = request.getParameterNames();
            while (attributes.hasMoreElements()) {
                String attribute = attributes.nextElement();
                data.setProperty(attribute, request.getParameter(attribute));
            }
        }
        return data;
    }
}
