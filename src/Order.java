

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

        /**
         * @return       String
         */
        public String getDestination()
        {
            return destination.toString();
        }


        /**
         * @return       String
         */
        public String getOrderer()
        {
            return orderedBy.toString();
        }
}
