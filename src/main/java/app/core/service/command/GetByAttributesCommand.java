package app.core.service.command;

import app.core.helper.ApplicationError;
import app.services.AbstractService;
import app.core.service.Command;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;

/**
 * Created by grom on 01/04/2017.
 * Project odc
 * author <grom25174@gmail.com>
 */
@Component
public class GetByAttributesCommand extends AbstractService implements Command {
    private Class<?> item;
    private Object result;
    private HashMap<String, Object> attributes;

    public void init() {
        this.item = null;
        this.result = null;
        this.attributes = null;
    }

    public void setType(Class<?> classType) {
        this.item = classType;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Object getResult() {
        return this.result;
    }

    @Transactional
    @Override
    public void execute() throws ApplicationError {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            StringBuilder queryString = new StringBuilder("from " + item.getName());
            if (attributes != null) {
                queryString.append(" where ");

                int count = 1;
                for (String key : attributes.keySet()) {
                    queryString.append(key + "=:" + key);
                    if (count < attributes.size()) {
                        queryString.append(" and ");
                    }
                    count++;
                }

                Query query = session.createQuery(queryString.toString(), item);
                for (String key : attributes.keySet()) {
                    query = query.setParameter(key, attributes.get(key));
                }

                result = query.getSingleResult();
            } else {
                result = session.createQuery(queryString.toString(), item).getSingleResult();
            }
            closeSession(session);
        } catch (NoResultException e) {
            result = null;
        } catch (Exception e) {
            closeSessionWithError(session, e);
        } finally {
            closeSession(session);
        }
    }
}
