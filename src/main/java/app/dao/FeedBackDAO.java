package app.dao;

import app.core.helper.ApplicationError;
import app.entities.FeedBack;

import java.util.List;

/**
 * Created by grom on 04/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */
public interface FeedBackDAO {
    FeedBack addNew(FeedBack entity) throws ApplicationError;

    List<FeedBack> getBySurname(String surname) throws ApplicationError;

    List<FeedBack> update(String surname, FeedBack newEntity) throws ApplicationError;
}
