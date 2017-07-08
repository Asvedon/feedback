package app.core.helper;

import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;

/**
 * Created by grom on 03/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */
public class ApplicationError extends Throwable {
    private String field;
    private String message;
    private Exception exception;

    public ApplicationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ApplicationError(Exception e) {
        ConstraintViolationException cve;
        Throwable cause = e;
        this.field = "error";

        while (cause != null && !(cause instanceof ConstraintViolationException)) {
            cause = cause.getCause();
        }
        if (cause == null) {
            this.message = e.getMessage();
        } else {
            cve = (ConstraintViolationException) cause;

            SQLException exception = cve.getSQLException();
            String mes = exception.getMessage();
            String constrain = cve.getConstraintName();

            this.message = mes.substring(mes.lastIndexOf(constrain) + constrain.length() + 1, mes.length() - 1).trim();
        }
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        if (message == null) {
            message = "";
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
