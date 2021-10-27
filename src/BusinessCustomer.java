import javax.naming.Name;
import java.util.ArrayList;

public class BusinessCustomer extends Customers{
    private Object MerchandiseItem;

    public BusinessCustomer(String Name, int ID) {
        super(Name, ID);
    }

    public double PayForOrder(MerchandiseItem.ItemType, ArrayList<ItemForSale>) {
        return 0;
        System.out.println("this order will be for" + Name + "and it will be paid with" + orderTotal);
        var orderTotal = MerchandiseItem + 6.25%;
        orderTotal + purchaseOrderBalance;

    }
    private String arrangeDelivery;


    public String getArrangeDelivery() {
        return arrangeDelivery;


    }

    public void setArrangeDelivery(String arrangeDelivery) {
        this.arrangeDelivery = arrangeDelivery;
        System.out.println("Hello," + Name + "all deliveries must be from 9-5 Mon-Fri");
    }

    public class purchaseOrderBalance();


}

