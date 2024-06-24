package modules;

import java.util.List;

public class Switch {

    private DataContainer container;
    private List<Warehouse> warehouseList;
    private List<Client> clientList;

    public Switch(DataContainer container){
        this.container = container;
        this.warehouseList = this.container.getWarehouses();
        this.clientList = this.container.getClients();
    }
}
