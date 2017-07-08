package app.core.service.command;

import app.core.helper.ApplicationError;
import app.services.AbstractService;
import app.core.service.Command;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by grom on 01/04/2017.
 * Project odc
 * author <grom25174@gmail.com>
 */
@Component
public class AddCommand extends AbstractService implements Command {
    private Object item;

    public void setItem(Object item) {
        this.item = item;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public void execute() throws ApplicationError {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            closeSession(session);
        } catch (Exception e) {
            closeSessionWithError(session, e);
        } finally {
            closeSession(session);
        }
    }
}
