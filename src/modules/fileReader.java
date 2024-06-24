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
    public List<> readFile(String fileName) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            String[] array = line.split("\\s+");
            int numWarehouses = Integer.parseInt(array[0]);
            int numClients = Integer.parseInt(array[1]);
            List<warehouse> warehouses = new ArrayList<>();
            List<client> clients = new ArrayList<>();
            for(int i = 0; i < numWarehouses; i++){
                String warehouseInfo = br.readLine();
                String[] warehouseArray = warehouseInfo.split("\\s+");
                int capacity = Integer.parseInt(warehouseArray[0]);
                float fixedCost = Float.parseFloat(warehouseArray[1]);
                warehouse warehouse = new warehouse(fixedCost, capacity);
                warehouses.add(warehouse);
                System.out.println("Capacity warehouse "+i+" : "+ warehouse.getCapacity());
                System.out.println("Fixed Cost warehouse "+i+" : "+ warehouse.getFixedCost());
            }


            for(int i = 0; i < numClients; i++){
                client client = new client();
                clients.add(client);
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


