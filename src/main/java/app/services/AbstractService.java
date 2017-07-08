package app.services;

import app.core.helper.ApplicationError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by grom on 14/02/2017.
 * Project shkoderi
 */
@Component
public abstract class AbstractService {
    @Autowired
    protected SessionFactory sessionFactory;

    public void closeSessionWithError(Session session, Exception e) throws ApplicationError {
        session.close();
        throw new ApplicationError(e);
    }

    public void closeSession(Session session) throws ApplicationError {
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (Exception e) {
                session.close();
                throw new ApplicationError(e);
            }
        }
    }
}
