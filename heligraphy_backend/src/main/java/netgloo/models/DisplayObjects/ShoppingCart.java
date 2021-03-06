package netgloo.models.DisplayObjects;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mazi on 18.04.17.
 */
@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart implements Serializable {

    public List<ShoppingCartItem> items;

    public double total;

    private String bildgruppe;

    public ShoppingCart(List<ShoppingCartItem> items, double total) {
        this.items = items;
        this.total = total;
    }

    public ShoppingCart() {
        this.items = new ArrayList<ShoppingCartItem>();
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getBildgruppe() {
        return bildgruppe;
    }

    public void setBildgruppe(String bildgruppe) {
        this.bildgruppe = bildgruppe;
    }
}
