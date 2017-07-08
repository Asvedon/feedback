package app.services;

import app.core.helper.ApplicationError;
import app.core.service.ServiceCommand;
import app.dao.FeedBackDAO;
import app.entities.FeedBack;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by grom on 04/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */
@Service
public class FeedBackService extends AbstractService implements FeedBackDAO {
    private ServiceCommand serviceCommand = new ServiceCommand();

    @Transactional
    @Override
    public FeedBack addNew(FeedBack entity) throws ApplicationError {
        if (entity.getSurname() == null || entity.getSurname().length() == 0) {
            Exception error = new InvalidPropertyException(FeedBack.class, "surname", "Не может быть пустым");
            ApplicationError exception = new ApplicationError("surname", "Не может быть пустым");
            exception.setException(error);
            throw exception;
        }
        if (entity.getQuantity() == null || entity.getQuantity() <= 0) {
            entity.setQuantity(0);
        }
        if (entity.getWillCome() == null) {
            entity.setWillCome(Boolean.TRUE);
        }

        return (FeedBack) serviceCommand.addEntity(entity, sessionFactory);
    }

    @Transactional
    @Override
    public List<FeedBack> getBySurname(String surname) throws ApplicationError {
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("surname", surname);
        List<FeedBack> result = (List<FeedBack>) serviceCommand.getList(FeedBack.class, attributes, -1, -1, null, sessionFactory);
        return result;
    }

    @Transactional
    @Override
    public List<FeedBack> update(String surname, FeedBack newEntity) throws ApplicationError {
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("surname", surname);
        List<FeedBack> guests = (List<FeedBack>) serviceCommand.getList(FeedBack.class, attributes, -1, -1, null, sessionFactory);

        if (guests != null && guests.size() > 0) {
            for (FeedBack guest : guests) {
                guest.setSurname(newEntity.getSurname());
                guest.setQuantity(newEntity.getQuantity());
                guest.setWillCome(newEntity.getWillCome());
                serviceCommand.updateEntity(guest, sessionFactory);
            }
        } else {
            Exception error = new NoResultException();
            ApplicationError exception = new ApplicationError("surname", "Запись не найдена");
            exception.setException(error);
            throw exception;
        }

        return guests;
    }
}
