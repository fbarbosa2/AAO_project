package modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * fileReader Class
 * This class is responsible for reading the .txt files and creating the Warehouse and Client Objects
 * and adding them to a DataContainer with Lists.
 */
public class fileReader {

    /**
     * Reads the file, parses the info of warehouses and clients, creates Warehouse and Client objects,
     * adds each of them to the correct list, and creates a DataContainer object to transport Warehouses
     * and Clients to the other modules.
     *
     * @param fileName The name of the file to be read.
     * @return A DataContainer object containing lists of warehouses and clients.
     * @throws FileNotFoundException If the specified file is not found.
     */
    public DataContainer readFile(String fileName) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Reading the number of warehouses and clients
            String line = br.readLine();
            String[] array = line.split("\\s+");
            if (array.length < 2) {
                throw new IOException("Invalid format for number of warehouses and clients.");
            }
            int numWarehouses = Integer.parseInt(array[0].strip());
            int numClients = Integer.parseInt(array[1].strip());

            List<Warehouse> warehouses = new ArrayList<>();
            List<Client> clients = new ArrayList<>();

            // Reading and creation of Warehouses
            for (int i = 0; i < numWarehouses; i++) {
                String warehousesInfo = br.readLine();
                String[] warehouseArray = warehousesInfo.split("\\s+");
                if (warehouseArray.length < 2) {
                    throw new IOException("Invalid format for warehouse information.");
                }
                float fixedCost = Float.parseFloat(warehouseArray[1]);
                Warehouse warehouse = new Warehouse(fixedCost);
                warehouses.add(warehouse);
            }

            // Reading and creation of Clients
            for (int i = 0; i < numClients; i++) {
                String cLine = br.readLine();

                if (cLine == null || cLine.trim().isEmpty()) {

                    cLine = br.readLine();
                }
                int demand = Integer.parseInt(cLine.strip());
                List<Float> allocationCosts = new ArrayList<>();

                int costsToRead = numWarehouses;
                while (allocationCosts.size() < costsToRead) {
                    line = br.readLine();
                    if (line == null) {
                        throw new IOException("Unexpected end of file while reading client costs.");
                    }
                    String[] costsArray = line.trim().split("\\s+");
                    for (String cost : costsArray) {
                        if (allocationCosts.size() < costsToRead) {
                            allocationCosts.add(Float.parseFloat(cost));
                        } else {
                            break; // Stop adding costs once we get all the costs attributed to that client
                        }
                    }
                }
                System.out.println("Alloc Cost client: "+ (i + 1) + " : ");
                for(float cost : allocationCosts){
                    System.out.println(cost);
                }
                Client client = new Client(allocationCosts);
                clients.add(client);
            }

            DataContainer container = new DataContainer(warehouses, clients);
            return container;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
