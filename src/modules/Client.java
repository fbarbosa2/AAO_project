package modules;

import java.util.List;

/**
 * Client class
 * This class represents a client object with a list of allocation costs to various warehouses.
 */
public class Client {

    private List<Float> allocCosts;

    /**
     * Constructor for Client
     *
     * @param allocCost List of allocation costs to different warehouses
     */
    public Client(List<Float> allocCost) {

        this.allocCosts = allocCost;
    }

    /**
     * Default constructor for Client
     * Initializes the allocCosts to null
     */
    public Client(){
        this.allocCosts = null;
    }

    /**
     * Getter method for allocation costs
     *
     * @return List of allocation costs to different warehouses
     */
    public List<Float> getAllocCosts() {
        return allocCosts;
    }

    /**
     * Setter method for allocation costs
     *
     * @param allocCost The new list of allocation costs
     */
    public void setAllocCosts(List<Float> allocCost) {
        this.allocCosts = allocCost;
    }
}
