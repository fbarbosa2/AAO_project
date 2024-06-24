package modules;

import java.util.List;

public class Greedy {
    private DataContainer container;
    private List<Warehouse> warehouseList;
    private List<Client> clientList;

    public Greedy(DataContainer container){
        this.container = container;
        this.warehouseList = this.container.getWarehouses();
        this.clientList = this.container.getClients();
    }
}
