package app.core.service.command;

/**
 * Created by grom on 23/05/2017.
 * Project odc
 * author <grom25174@gmail.com>
 */
public class OrderList {
    private String attribute = new String();
    private boolean ascending = true;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
