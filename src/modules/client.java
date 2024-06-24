package modules;

public class client {
    private int demand;
    private float allocCost;

    public client(int demand, float allocCost) {
        this.demand = demand;
        this.allocCost = allocCost;
    }
    public client(){
        this.allocCost = 0;
        this.demand = 0;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public float getAllocCost() {
        return allocCost;
    }

    public void setAllocCost(float allocCost) {
        this.allocCost = allocCost;
    }
}
