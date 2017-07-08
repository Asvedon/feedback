package app.views;

import java.util.HashMap;

/**
 * Created by grom on 04/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */
public interface ApiViewInterface<T> {
    /**
     * Method which used for initialization view model
     *
     * @param entity entity for view
     */
    void initVeiw(T entity);

    /**
     * Method which used to get HashMap with view fields
     *
     * @return HashMap
     */
    HashMap<String, Object> getView();
}
