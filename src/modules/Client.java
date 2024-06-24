package modules;

import java.util.List;

public class Client {
    private int demand;
    private List<Float> allocCosts;

    public Client(int demand, List<Float> allocCost) {
        this.demand = demand;
        this.allocCosts = allocCost;
    }
    public Client(){
        this.allocCosts = null;
        this.demand = 0;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public List<Float> getAllocCosts() {
        return allocCosts;
    }

    public void setAllocCosts(List<Float> allocCost) {
        this.allocCosts = allocCost;
    }
}
