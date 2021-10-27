import java.util.ArrayList;

/**
     * Class Order
     */
    public class Order {

        //
        // Fields
        //

        private ShippingAddress destination;
        private Customers orderedBy;


        /**
         * @param        dest
         * @param        cust
         */
        public Order(ShippingAddress dest, Customers cust)
        {
            destination = dest;
            orderedBy = cust;
        }

        //
        // Methods
        //


        public String getDestination()
        {
            return destination.toString();
        }



        public String getOrderer()
        {
            return orderedBy.toString();
        }

    private Object MerchandiseItem;
    private final double cartForOrder = ArrayList<MerchandiseItem>;
    }
