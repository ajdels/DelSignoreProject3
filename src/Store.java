

import javax.imageio.IIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;


    public class Store {

        //
        // Fields
        //

        private ArrayList<Order> Orders;
        private ArrayList<Customers> Customers;

        //
        // Constructors
        //
        public Store () {
            Orders = new ArrayList<Order>();
            Customers = new ArrayList<Customers>();
        }

        //
        // Methods
        //




        public static void main(String[] args) throws IOException
        {
            var comp152Inc = new Store();
            comp152Inc.runStore();

        }


        public void runStore() throws IOException
        {
            var inputReader = new Scanner(System.in);
            loadStartingCustomers(inputReader);
            while(true){ //the main run loop
                printMainMenu();
                var userChoice = inputReader.nextInt();
                switch (userChoice){
                    case 1:
                        addCustomer(inputReader);
                        break;
                    case 2:
                        var selectedCustomer =selectCustomer(inputReader);
                        if(selectedCustomer.isPresent())
                            manageCustomer(selectedCustomer.get());
                        break;
                    case 3:
                        System.exit(0);

                    default:
                        System.out.println("\n%%%%%%Invalid selection, please choose one of the options from the menu%%%%%%\n");
                }
            }
        }

        private static void printMainMenu() {
            System.out.println("*****************************************************************************");
            System.out.println("Welcome to the the 1980s Comp152 Store interface, what would you like to do?");
            System.out.println("   [1] Add Customer");
            System.out.println("   [2] Select Customer");
            System.out.println("   [3] Exit the program");
            System.out.println("*****************************************************************************");
            System.out.print("Enter the number of your choice:");
        }




        private void loadStartingCustomers(Scanner inputReader) throws IOException {
            Path fullPathName;
            String filename;
            while(true) { //this is for some error checking. It was not required by the assignment
                System.out.print("Enter the name of the file to load customers:");
                filename = inputReader.nextLine();
                fullPathName = Paths.get(filename);
                if (!Files.exists(fullPathName)){ //these three lines checks to see if the file exists, if not go
                    System.out.println("No file with that name, please try again....");//do the loop again
                }
                else
                    break;
            }
            //if we got here the file must be real
            var allLines = Files.readAllLines(fullPathName);
            // now create customers for all of the lines in the file
            for(var line: allLines){
                var splitLine = line.split(",");
                var currentCustomer = new Customers(splitLine[0], Integer.parseInt(splitLine[1]));
                Customers.add(currentCustomer);
            }
        }


        /**
         * @param        address
         * @param        cust
         */
        public void makeOrder(ShippingAddress address, Customers cust)
        {
            var newOrder = new Order(address,cust);
            Orders.add(newOrder);
            System.out.println(".......New order successfully created");
        }




        public void addCustomer(Scanner inputReader)
        {
            //because we just came from a nextInt, there is an orphaned \n on the input stream eat it
            inputReader.nextLine();
            System.out.println("Adding Customer........");
            System.out.print("Enter the new Customers name:");
            var newName = inputReader.nextLine();
            var newCustomer = new Customers(newName);
            Customers.add(newCustomer);
            System.out.println(".....Finished adding new Customer Record");
        }



        public Optional<Customers> selectCustomer(Scanner reader)
        {
            System.out.print("Enter the ID of the customer to select:");
            var enteredID = reader.nextInt();
            for(var currentCustomer: Customers){
                if(currentCustomer.getCustomerID()==enteredID)
                    return Optional.of(currentCustomer);
            }
            //If we looked through the whole list and didn't find that customer tell the user
            System.out.println("==========================> No customer with customer ID:"+enteredID);
            return Optional.empty();
        }


        /**
         * @param        selectedCustomer
         */
        public void manageCustomer(Customers selectedCustomer)
        {
            //many of you passed our existing scanner in as an extra parameter, that is more than fine.
            //I'm doing it this way to create a new one, both work the same way since both are reading from
            //System.in
            Scanner secondScanner = new Scanner(System.in);
            while(true){ //the menu loop for the manage Customer menu
                printCustomerMenu(selectedCustomer.getName());
                var userChoice = secondScanner.nextInt();
                //the syntax below is part of the 'enhanced switch' available in very recent versions of java
                //I used the traditional version in the previous switch above, so I'm experimenting here
                //this 'enhanced switch' doesn't require break statements.
                switch (userChoice){
                    case 1 ->addAddress(secondScanner, selectedCustomer);
                    case 2->{
                        ShippingAddress selectedAddress = pickAddress(secondScanner,selectedCustomer);
                        makeOrder(selectedAddress,selectedCustomer);
                    }
                    case 3-> {return;}
                    default->System.out.println("Invalid option selected");
                }
            }
        }

        private ShippingAddress pickAddress(Scanner secondScanner, Customers selectedCustomer) {
            var customerAddresses = selectedCustomer.getAddresses();
            if (customerAddresses.size() ==0){ //note, this error checking was not required
                System.out.println("This customer has no addresses on file, please add an address");
                addAddress(secondScanner,selectedCustomer);
                return selectedCustomer.getAddresses().get(0); //if we are here there is only one address so use it
            }
            var count = 0;
            //if we are here then there was at least one address already in the customer.
            System.out.println("Please select a shipping address from those the customer has on file");
            for (var address : customerAddresses) {   //I'm being a little 'cute'/clever here
                System.out.print("[" + count + "]"); //but you could do for(int count; count < customerAddresses.size(); count++ for the same effect
                System.out.println(address.toString());
                count++;
            }
            System.out.print("Enter the number of the address for this order:");
            var addressNum = secondScanner.nextInt();
            //again more error checking that I didn't require of you here below:
            if (addressNum >= customerAddresses.size()){
                System.out.println("Invalid entry, defaulting to the first address on file...");
                return customerAddresses.get(0);
            }
            else
                return customerAddresses.get(addressNum);//I asked the user for the number representing the addresses position in the arrayList
        }

        private void addAddress(Scanner secondScanner, Customers selectedCustomer) {
            System.out.println("Adding new address for "+ selectedCustomer.getName());
            secondScanner.nextLine(); //lets eat the leftover '\n' from previous nextInt
            System.out.print("Enter Address Line 1:");
            var line1 = secondScanner.nextLine();
            System.out.print("Enter Address Line 2 or <enter> if there is none:");
            var line2 = secondScanner.nextLine();
            System.out.print("Enter the address City:");
            var city = secondScanner.nextLine();
            System.out.print("Enter address state:");
            var state = secondScanner.nextLine();
            System.out.print("Enter the postal code:");
            var postCode = secondScanner.nextLine();
            var newAddress  = new ShippingAddress(line1,line2,city,state,postCode);
            selectedCustomer.addAddress(newAddress);
        }

        private void printCustomerMenu(String custName) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("What do you want to do for Customer " + custName+"?");
            System.out.println("   [1] Add Address to customer");
            System.out.println("   [2] Make an order for the customer");
            System.out.println("   [3] return to the main menu");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.print("Enter the number of your choice:");
        }


    }


