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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by grom on 31/03/2017.
 * Project odc
 * author <grom25174@gmail.com>
 */
@Component
public class GetListCommand extends AbstractService implements Command {
    private Class<?> item = null;
    private List<?> result = null;
    private int limit = -1;
    private int offset = -1;
    private OrderList order = null;
    private HashMap<String, Object> attributes = null;

    public void init() {
        item = null;
        result = null;
        limit = -1;
        offset = -1;
        order = null;
        attributes = null;
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

    public void setLimits(int limit, int offset, OrderList order) {
        this.limit = limit;
        this.offset = offset;
        this.order = order;
    }

    public List<?> getResult() {
        return this.result;
    }

    @Transactional
    @Override
    public void execute() throws ApplicationError {
        Session session = null;
        try {
            session = sessionFactory.openSession();

            Query query;
            if (limit >= 0 || offset >= 0 || order != null) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery criteria = builder.createQuery(item);
                Root root = criteria.from(item);
                if (attributes != null) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    for (String key : attributes.keySet()) {
                        predicates.add(builder.equal(root.get("key"), attributes.get(key)));
                    }

                    criteria.where(predicates.toArray(new Predicate[]{}));
                }
                if (order != null && order.getAttribute().length() > 0) {
                    if (order.isAscending()) {
                        criteria.orderBy(builder.asc(root.get(order.getAttribute())));
                    } else {
                        criteria.orderBy(builder.desc(root.get(order.getAttribute())));
                    }
                }

                query = session.createQuery(criteria);
                if (limit >= 0) {
                    query.setMaxResults(limit);
                }
                if (offset >= 0) {
                    query.setFirstResult(offset);
                }
            } else {
                StringBuilder queryString = new StringBuilder("from " + item.getName());
                if (attributes != null && attributes.size() > 0) {
                    queryString.append(" where ");

                    int count = 1;
                    for (String key : attributes.keySet()) {
                        queryString.append(key + "=:" + key);
                        if (count < attributes.size()) {
                            queryString.append(" and ");
                        }
                        count++;
                    }
                }

                query = session.createQuery(queryString.toString(), item);
                if (attributes != null) {
                    for (String key : attributes.keySet()) {
                        query = query.setParameter(key, attributes.get(key));
                    }
                }
            }

            result = query.getResultList();

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
