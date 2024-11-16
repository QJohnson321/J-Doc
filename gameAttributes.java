/**
 * This class is the getter and setter class for the
 * attributes of each game
 */
public class gameAttributes {


    /**
     * Unique identifier for the game
     */
    private int gameid;
    /**
     * Title of the game
     */
    private String title;
    /**
     * Genre of the game
     */
    private String genre;
    /**
     * Release date of the game
     */
    private String releaseDMYear;
    /**
     * Console on which the game can be played
     */
    private String console;
    /**
     * The game came out as a disk,cartridge,digital etc
     */
    private String productId;
    /**
     * For checking if game allows multiplayer
     */
    private String multiplayer;
    /**
     * Studio that created the game
     */
    private String studio;
    /**
     * For price of a game
     */
    private double price;

    /**
     * Description of items
     */
    private String items;
    /**
     * Number of copies user wants
     */
    private int quantity;
    //private double price;

    /**
     *
     * @param gameid constructor for game id
     * @param title constructor for title
     * @param genre constructor for genre
     * @param releaseDMYear constructor for release date
     * @param console constructor for console
     * @param studio constructor for studio
     * @param productId constructor for productId
     * @param multiplayer constructor for multiplayer
     * @param price constructor for price
     */
    public gameAttributes(int gameid, String title, String genre, String releaseDMYear,
                          String console, String studio, String productId, String multiplayer, double price) {

        this.gameid = gameid;
        this.title = title;
        this.genre = genre;
        this.releaseDMYear = releaseDMYear;
        this.console = console;
        this.studio = studio;
        this.productId = productId;
        this.multiplayer = multiplayer;

        this.items = items;
        this.price = price;
        this.quantity = quantity;

    }

    /**
     *
     * @param gameId constructor for game id
     * @param title constructor for title
     * @param price constructor for price
     */
    public gameAttributes(int gameId, String title, double price) {
        this.gameid = gameId;
        this.title = title;
        this.price = price;
    }

    /**
     *
     * @return the game id
     */
    public int getGameid() {
        return gameid;
    }

    /**
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     *
     * @return the release year
     */
    public String getReleaseDMYear() {
        return releaseDMYear;
    }

    /**
     *
     * @return the console
     */
    public String getConsole() {
        return console;
    }

    /**
     *
     * @return the studio
     */
    public String getStudio() {
        return studio;
    }

    /**
     *
     * @return the product id
     */

    public String getProductId() {
        return productId;
    }

    /**
     *
     * @return the multiplayer
     */
    public String getMultiplayer() {
        return multiplayer;
    }

    /**
     *
     * @return the price of a game
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param title sets the title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     *
     * @param genre sets the genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    /**
     *
     * @param releaseDMYear sets the release date
     */
    public void setReleaseDMYear(String releaseDMYear) {
        this.releaseDMYear = releaseDMYear;
    }

    /**
     *
     * @param console sets the console of the game
     */
    public void setConsole(String console) {
        this.console = console;
    }

    /**
     *
     * @param studio this sets the studio
     */
    public void setStudio(String studio) {
        this.studio = studio;
    }


    /**
     *
     * @return the attributes for the setters and getters
     */
    public String toString() {
        return "Game Id: "  + gameid + " \nTitle: " + title + " \nGenre: " + genre + " \nReleaseDMYear: "+ releaseDMYear +
                " \nConsole: " + console + " \nStudio: " + studio + " \nMultiplayer: " + multiplayer + " \nPrice: $ " +price;
    }
}

