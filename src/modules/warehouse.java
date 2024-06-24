package modules;

public class warehouse {
    private boolean open;
    private float fixedCost;
    private int capacity;

    public warehouse (){
        this.open = false;
        this.fixedCost = 0;
        this.capacity = 0;
    }

    public warehouse(float fixedCost, int capacity){
        this.open = false;
        this.fixedCost = fixedCost;
        this.capacity = capacity;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public float getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(float fixedCost) {
        this.fixedCost = fixedCost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}



