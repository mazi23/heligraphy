package netgloo.models.DisplayObjects;

import org.springframework.stereotype.Component;

/**
 * Created by mazi on 19.04.17.
 */
@Component
public class ShoppingCartItem {

     private int id;
     private int price;
     private int quantity;


    public ShoppingCartItem(int id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }


    public ShoppingCartItem() {
    }


    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
