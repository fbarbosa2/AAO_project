package modules;

/**
 * Warehouse class
 * This class represents a warehouse object with a fixed cost and an open/closed status.
 */
public class Warehouse {
    private boolean open;
    private float fixedCost;


    /**
     * Default constructor for Warehouse
     * Initializes the warehouse as closed with a fixed cost of 0
     */
    public Warehouse(){
        this.open = false;
        this.fixedCost = 0;
    }

    /**
     * Constructor for Warehouse with specified fixed cost
     * Initializes the warehouse as closed with the provided fixed cost
     *
     * @param fixedCost The fixed cost of the warehouse
     */
    public Warehouse(float fixedCost){
        this.open = false;
        this.fixedCost = fixedCost;
    }

    /**
     * Getter method for the open status
     *
     * @return true if the warehouse is open, false otherwise
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Setter method for the open status
     *
     * @param open The new open status of the warehouse
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * Getter method for the fixed cost
     *
     * @return The fixed cost of the warehouse
     */
    public float getFixedCost() {
        return fixedCost;
    }

    /**
     * Setter method for the fixed cost
     *
     * @param fixedCost The new fixed cost of the warehouse
     */
    public void setFixedCost(float fixedCost) {
        this.fixedCost = fixedCost;
    }

}



