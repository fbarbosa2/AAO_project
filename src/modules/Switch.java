package modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Switch {

    private DataContainer container;
    private List<Warehouse> warehouseList;
    private List<Client> clientList;
    private List<Integer> openedWarehousesIndex;
    private List<Integer> closedWarehousesIndex;
    private float bestSolution;
    private Random rand;

    public Switch(DataContainer container){
        this.container = container;
        this.warehouseList = this.container.getWarehouses();
        this.clientList = this.container.getClients();
        this.bestSolution = 0;
        this.openedWarehousesIndex = new ArrayList<>();
        this.closedWarehousesIndex = new ArrayList<>();
        this.rand = new Random();
    }
    private void getInitialSolution(){

        float currentSolution = 0;

        // Initialize all warehouses as closed
        for (int i = 0; i < warehouseList.size(); i++) {
            warehouseList.get(i).setOpen(false);
            closedWarehousesIndex.add(i);
        }

        // Open warehouses with even indices
        for (int i = 0; i < warehouseList.size(); i++) {
            if (i % 2 == 0) {
                warehouseList.get(i).setOpen(true);
                openedWarehousesIndex.add(i);
                closedWarehousesIndex.remove(Integer.valueOf(i));
            }
        }

        currentSolution = calculateSolutionCost();
        this.bestSolution = currentSolution;

    }
    private void performSwitch() {
        // Loop until no further improvement or convergence criteria met
        int MAX_ITERATIONS = 10000;
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // Randomly select warehouses to potentially switch
            int randomOpenIndex = rand.nextInt(openedWarehousesIndex.size());
            int randomClosedIndex = rand.nextInt(closedWarehousesIndex.size());

            int openWarehouse = openedWarehousesIndex.get(randomOpenIndex);
            int closedWarehouse = closedWarehousesIndex.get(randomClosedIndex);

            // Simulate switch: close one warehouse and open another
            warehouseList.get(openWarehouse).setOpen(false);
            warehouseList.get(closedWarehouse).setOpen(true);

            // Calculate new solution cost
            float newSolutionCost = calculateSolutionCost();

            // If the switch improves the solution, update bestSolution and indexes
            if (newSolutionCost < this.bestSolution) {
                this.bestSolution = newSolutionCost;
                openedWarehousesIndex.set(randomOpenIndex, closedWarehouse);
                closedWarehousesIndex.set(randomClosedIndex, openWarehouse);
            } else {
                // Revert the switch if it does not improve the solution
                warehouseList.get(openWarehouse).setOpen(true);
                warehouseList.get(closedWarehouse).setOpen(false);
            }
        }
    }

    public void useSwitch(){
        long start = System.nanoTime();

        getInitialSolution();
        System.out.println("Initial solution cost: " + this.bestSolution);
        performSwitch();

        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        System.out.println("Best Soluction Found: " + this.bestSolution);
        System.out.println("Time elapsed: " + elapsedTimeInSeconds + " seconds");
    }

    /**
     * Calculates the current solution cost.
     * This includes the fixed costs of open warehouses and allocation costs to clients.
     * @return Total cost of the current solution.
     */
    private float calculateSolutionCost() {
        float totalCost = 0;

        // Calcular custo fixo das instalações abertas
        for (Warehouse warehouse : warehouseList) {
            if (warehouse.isOpen()) {
                totalCost += warehouse.getFixedCost();
            }
        }

        // Calcular custo de alocação dos clientes para as instalações abertas
        // Percorre todos os clients e todas as warehouses de forma a o client ter o
        // o custo da warehouse mais baixo
        for (Client client : clientList) {
            float minAllocCost = Float.MAX_VALUE;
            for (int i = 0; i < warehouseList.size(); i++) {
                if (warehouseList.get(i).isOpen()) {
                    minAllocCost = Math.min(minAllocCost, client.getAllocCosts().get(i));
                }
            }
            totalCost += minAllocCost;
        }

        return totalCost;
    }
}
