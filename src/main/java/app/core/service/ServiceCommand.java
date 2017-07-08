package app.core.service;

import app.core.helper.ApplicationError;
import app.core.service.command.*;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by grom on 01/04/2017.
 * Project odc
 * author <grom25174@gmail.com>
 */
public class ServiceCommand {
    private GetListCommand getList = new GetListCommand();
    private GetByAttributesCommand getByAttributes = new GetByAttributesCommand();
    private AddCommand addEntity = new AddCommand();
    private UpdateCommand updateEntity = new UpdateCommand();
    private DeleteCommand deleteEntity = new DeleteCommand();

    public List<?> getList(Class<?> classType, SessionFactory sessionFactory) throws ApplicationError {
        this.getList.init();
        this.getList.setSessionFactory(sessionFactory);
        this.getList.setType(classType);
        this.getList.execute();
        return this.getList.getResult();
    }

    public List<?> getList(Class<?> classType, HashMap attributes, int limit, int offset, OrderList order, SessionFactory sessionFactory) throws ApplicationError {
        this.getList.init();
        this.getList.setSessionFactory(sessionFactory);
        this.getList.setType(classType);
        this.getList.setAttributes(attributes);
        this.getList.setLimits(limit, offset, order);
        this.getList.execute();
        return this.getList.getResult();
    }

    public Object getByAttributes(Class<?> classType, HashMap attributes, SessionFactory sessionFactory) throws ApplicationError {
        this.getByAttributes.init();
        this.getByAttributes.setSessionFactory(sessionFactory);
        this.getByAttributes.setType(classType);
        this.getByAttributes.setAttributes(attributes);
        this.getByAttributes.execute();
        return this.getByAttributes.getResult();
    }

    public Object addEntity(Object item, SessionFactory sessionFactory) throws ApplicationError {
        this.addEntity.setSessionFactory(sessionFactory);
        this.addEntity.setItem(item);
        this.addEntity.execute();
        return item;
    }

    public Object updateEntity(Object item, SessionFactory sessionFactory) throws ApplicationError {
        this.updateEntity.setSessionFactory(sessionFactory);
        this.updateEntity.setItem(item);
        this.updateEntity.execute();
        return item;
    }

    public Object deleteEntity(Object item, SessionFactory sessionFactory) throws ApplicationError {
        this.deleteEntity.setSessionFactory(sessionFactory);
        this.deleteEntity.setItem(item);
        this.deleteEntity.execute();
        return item;
    }
}