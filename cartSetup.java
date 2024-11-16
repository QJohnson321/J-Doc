/**
 * This is the getters and setters class for
 * the users cart transaction.
 *
  */
public class cartSetup {
    /**
     * The title of the game
     */
    private String title;
    /**
     * The price of the game
     */
    private double price;
    /**
     * The quantity of the game
     */
    private int quantity;


    /**
     * This is the constructor for the cart
     * @param title video game title
     * @param price video games price
     */
        public cartSetup(String title,  double price) {
            this.title = title;
            this.price = price;
            this.quantity = 1;


        }

    /**
     *
     * @return get a games title
     */
    public String getTitle() {
            return title;
        }

    /**
     *
     * @return get a games price
     */
    public double getPrice() {
            return price;
        }

    /**
     *
     * @return get a games quantity
     */
        public int getQuantity() {
            return quantity;
        }

       // public void setItems(String items) {
        //    this.items = items;
       // }

    /**
     *
     * @param price allows us to change the price
     */
        public void setPrice(double price) {
            this.price = price;
        }

    /**
     *
     * @param quantity allows us to change the quantity
     */
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    /**
     * add one more quantity if the game has the same title
     */
        public void incrementQuantity() {
            this.quantity++;
        }

    }

