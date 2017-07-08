package app.core.service;

import app.core.helper.ApplicationError;

/**
 * Created by grom on 31/03/2017.
 * Project odc
 * author <grom25174@gmail.com>
 */
public interface Command {
    void execute() throws ApplicationError;
}
