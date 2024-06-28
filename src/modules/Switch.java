package modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Switch {

    private DataContainer container;
    private List<Warehouse> warehouseList;

    private List<Client> clientList;
    private float bestSolutionCost;
    private List<Boolean> bestSolution;
    private List<Boolean> currentSolution;

    private Random rand;

    public Switch(DataContainer container){
        this.container = container;
        this.warehouseList = this.container.getWarehouses();
        this.clientList = this.container.getClients();
        this.bestSolutionCost = 0;
        this.rand= new Random();
        this.bestSolution = new ArrayList<>();
        this.currentSolution = new ArrayList<>();
    }
    private void getInitialSolution(){

        float currentSolution = 0;
        List<Boolean> current = new ArrayList<>();

        // Inicializar listas de instalações abertas e fechadas
        for (int i = 0; i < warehouseList.size(); i++) {
            current.add(false);

        }

        // Open warehouses with even indices
        for (int i = 0; i < warehouseList.size(); i++) {
            if (i % 2 == 0) {
                current.set(i, true);
            }
        }

        this.currentSolution = current;
        this.bestSolutionCost = calculateSolutionCost(current);

    }

    private List<List<Boolean>> generateNeighbours(){
        List<List<Boolean>> neighbours = new ArrayList<>();

        for(int i = 0; i < warehouseList.size(); i++){
            List<Boolean> neighbourSolution = new ArrayList<>(this.currentSolution);
            neighbourSolution.set(i,false);
            neighbours.add(neighbourSolution);
        }
        return neighbours;
    }

    private void localSearch(){
        boolean improvement = true;

        while(improvement){
            improvement = false;
            List<List<Boolean>> neighbours = generateNeighbours();

            for(List<Boolean> neighbour : neighbours){
                float neighbourCost = calculateSolutionCost(neighbour);
                if(neighbourCost < this.bestSolutionCost){
                    this.bestSolutionCost = neighbourCost;
                    this.bestSolution = neighbour;
                    improvement = true;
                }
            }
            this.currentSolution = this.bestSolution;

        }

    }
    /*
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

     */

    public void useSwitch(){
        long start = System.nanoTime();

        getInitialSolution();
        System.out.println("Initial solution cost: " + this.bestSolutionCost);
        localSearch();

        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        System.out.println("Best Soluction Found: " + this.bestSolutionCost);
        System.out.println("Time elapsed: " + elapsedTimeInSeconds + " seconds");
    }

    /**
     * Calculates the current solution cost.
     * This includes the fixed costs of open warehouses and allocation costs to clients.
     * @return Total cost of the current solution.
     */
    private float calculateSolutionCost(List<Boolean> solution) {
        float totalCost = 0;

        // Calcular custo fixo das instalações abertas
        for(int i = 0; i < solution.size(); i++){
            if(solution.get(i)){
                totalCost+= warehouseList.get(i).getFixedCost();
            }
        }

        // Calcular custo de alocação dos clientes para as instalações abertas
        // Percorre todos os clients e todas as warehouses de forma a o client ter o
        // o custo da warehouse mais baixo
        for (Client client : clientList) {
            float minAllocCost = Float.MAX_VALUE;
            for (int i = 0; i <solution.size(); i++) {
                if (solution.get(i)) {
                    minAllocCost = Math.min(minAllocCost, client.getAllocCosts().get(i));
                }
            }
            totalCost += minAllocCost;
        }

        return totalCost;
    }
}
