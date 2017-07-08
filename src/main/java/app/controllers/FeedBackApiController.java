package app.controllers;

import app.core.helper.ApplicationError;
import app.dao.FeedBackDAO;
import app.entities.FeedBack;
import app.views.ApiAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by grom on 04/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */
@RestController
@RequestMapping(value = "/api", method = {RequestMethod.GET, RequestMethod.POST})
public class FeedBackApiController extends AbstractRestController {
    @Autowired
    private FeedBackDAO feedBackServce;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiAnswer register(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        Properties data;
        HashMap<String, Object> result = new HashMap<>();

        String test = request.getContentType();
        Enumeration<String> test1 = request.getParameterNames();
        Map<String, String[]> test2 = request.getParameterMap();

        try {
            data = getRequestData(request);

            FeedBack entity = new FeedBack();
            if (data.getProperty("surname") != null) {
                entity.setSurname(data.getProperty("surname"));
            }
            if (data.getProperty("quantity") != null) {
                entity.setQuantity(Integer.parseInt(data.getProperty("quantity")));
            }
            if (data.getProperty("will_come") != null) {
                entity.setWillCome(Boolean.parseBoolean(data.getProperty("will_come")));
            }

            FeedBack addResult = feedBackServce.addNew(entity);

            result.clear();
            result.put("id", addResult.getId());
            result.put("surname", addResult.getSurname());
            result.put("quantity", addResult.getQuantity());
            result.put("will_come", addResult.getWillCome());

            prepareAnswer(response, HttpStatus.OK, HttpServletResponse.SC_OK, result);
        } catch (ApplicationError error) {
            result.clear();
            result.put(error.getField(), error.getMessage());
            prepareAnswer(response, HttpStatus.INTERNAL_SERVER_ERROR, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, result);
        } catch (IOException error) {
            result.clear();
            result.put("error", error.getMessage());
            prepareAnswer(response, HttpStatus.INTERNAL_SERVER_ERROR, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, result);
        }


        return answer;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiAnswer update(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        Properties data;
        HashMap<String, Object> result = new HashMap<>();
        try {
            data = getRequestData(request);

            FeedBack entity = new FeedBack();
            if (data.getProperty("surname") != null) {
                entity.setSurname(data.getProperty("surname"));
            }
            if (data.getProperty("quantity") != null) {
                entity.setQuantity(Integer.parseInt(data.getProperty("quantity")));
            }
            if (data.getProperty("will_come") != null) {
                entity.setWillCome(Boolean.parseBoolean(data.getProperty("will_come")));
            }

            List<FeedBack> updResult = feedBackServce.getBySurname(entity.getSurname());
            if (updResult != null && updResult.size() > 0) {
                feedBackServce.update(entity.getSurname(), entity);
            } else {
                entity = feedBackServce.addNew(entity);
            }
            result.clear();
            result.put("id", entity.getId());
            result.put("surname", entity.getSurname());
            result.put("quantity", entity.getQuantity());
            result.put("will_come", entity.getWillCome());

            prepareAnswer(response, HttpStatus.OK, HttpServletResponse.SC_OK, result);
        } catch (ApplicationError error) {
            result.clear();
            result.put(error.getField(), error.getMessage());
            prepareAnswer(response, HttpStatus.INTERNAL_SERVER_ERROR, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, result);
        } catch (IOException error) {
            result.clear();
            result.put("error", error.getMessage());
            prepareAnswer(response, HttpStatus.INTERNAL_SERVER_ERROR, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, result);
        }


        return answer;
    }
}
