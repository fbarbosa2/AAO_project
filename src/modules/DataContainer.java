package modules;
import java.util.List;

public class DataContainer {
    private List<Warehouse> warehouses;
    private List<Client> clients;

    public DataContainer(List<Warehouse> warehouses, List<Client> clients) {
        this.warehouses = warehouses;
        this.clients = clients;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public List<Client> getClients() {
        return clients;
    }
}
