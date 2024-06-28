package modules;
import java.util.List;

/**
 * DataContainer class
 * This class acts as a container for the lists of Warehouse and Client objects.
 */
public class DataContainer {
    private List<Warehouse> warehouses;
    private List<Client> clients;

    /**
     * Constructor for DataContainer
     *
     * @param warehouses List of Warehouse objects to be stored in the container
     * @param clients List of Client objects to be stored in the container
     */
    public DataContainer(List<Warehouse> warehouses, List<Client> clients) {
        this.warehouses = warehouses;
        this.clients = clients;
    }

    /**
     * Getter method for warehouses
     *
     * @return List of Warehouse objects stored in the container
     */
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Getter method for clients
     *
     * @return List of Client objects stored in the container
     */
    public List<Client> getClients() {
        return clients;
    }
}
