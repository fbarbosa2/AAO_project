package modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class fileReader {

    /**
     * Reads the file, parses the info of warehouses and clients, and creates Warehouse and Client objects.
     * @param fileName The name of the file to be read.
     */
    public DataContainer readFile(String fileName) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            String[] array = line.split("\\s+");
            if (array[0].equals("")){
                array[0] = array[1];
                array[1] = array[2];
            }
            int numWarehouses = Integer.parseInt(array[0].strip());
            int numClients = Integer.parseInt(array[1].strip());
            List<Warehouse> warehouses = new ArrayList<>();
            List<Client> clients = new ArrayList<>();

            //Reading and Creation of Warehouses
            for(int i = 0; i < numWarehouses; i++){
                String warehousesInfo = br.readLine();
                String[] warehouseArray = warehousesInfo.split("\\s+");
                if (warehouseArray[0].equals("")){
                    warehouseArray[0] = warehouseArray[1];
                    warehouseArray[1] = warehouseArray[2];
                }
                int capacity = Integer.parseInt(warehouseArray[0]);
                float fixedCost = Float.parseFloat(warehouseArray[1]);
                Warehouse warehouse = new Warehouse(fixedCost, capacity);
                warehouses.add(warehouse);
                System.out.println("warehouse " + i + " capacity : " + warehouse.getCapacity());
                System.out.println("warehouse " + i + " fixedCost : " + warehouse.getFixedCost());
            }

            //Reading and Creation of Clients
            for (int i = 0; i < numClients; i++) {
                int demand = Integer.parseInt(br.readLine().strip());
                List<Float> allocationCosts = new ArrayList<>();

                // Read until we have enough costs for the current client
                int costsToRead = numWarehouses;
                while (allocationCosts.size() < costsToRead) {
                    line = br.readLine();
                    String[] costsArray = line.trim().split("\\s+");
                    for (String cost : costsArray) {
                        if (allocationCosts.size() < costsToRead) {
                            allocationCosts.add(Float.parseFloat(cost));
                        } else {
                            break; // Stop adding costs once we have enough
                        }
                    }
                }

                // Create Client object and add to list
                Client client = new Client(demand, allocationCosts);
                clients.add(client);

                // For debugging or verification, print client details
                System.out.println("Client " + i + " demand: " + client.getDemand());
                for (Float cost : client.getAllocCosts()) {
                    System.out.print(cost + " ");
                }
                System.out.println(); // Move to next line after printing costs
            }
            DataContainer container = new DataContainer(warehouses, clients);

            return container;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}


