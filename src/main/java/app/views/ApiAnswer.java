package app.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * Created by grom on 04/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */
public class ApiAnswer {
    @JsonIgnore
    private HttpStatus status;
    @JsonProperty("status")
    private int code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("details")
    private HashMap details;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
        this.code = status.value();
        this.message = status.getReasonPhrase();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap getDetails() {
        return details;
    }

    public void setDetails(HashMap details) {
        this.details = details;
    }
}