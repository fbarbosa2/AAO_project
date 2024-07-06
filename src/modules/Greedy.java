package modules;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a Greedy algorithm for solving the Facility Location Problem.
 */
public class Greedy {

    private DataContainer container; // Data container holding warehouses and clients
    private List<Warehouse> warehouseList; // List of warehouses
    private List<Client> clientList; // List of clients
    private float bestSolutionCost; // Cost of the best solution found
    private List<Boolean> bestSolution; // List indicating the state of warehouses in the best solution
    private List<Boolean> currentSolution; // Current solution being evaluated

    /**
     * Constructor for Greedy algorithm.
     * @param container Data container containing warehouses and clients.
     */
    public Greedy(DataContainer container){
        this.container = container;
        this.warehouseList = this.container.getWarehouses();
        this.clientList = this.container.getClients();
        this.bestSolutionCost = 0;
        this.bestSolution = new ArrayList<>();
        this.currentSolution = new ArrayList<>();
    }

    /**
     * Generates the initial solution where all warehouses are open.
     */
    private void getInitialSolution(){
        List<Boolean> current = new ArrayList<>();

        // Initialize all warehouses as open
        for(Warehouse warehouse : warehouseList){
            current.add(true);
        }

        this.currentSolution = current;
        this.bestSolution = current;
        this.bestSolutionCost = calculateSolutionCost(current);
    }

    /**
     * Performs the Greedy algorithm to find the best solution.
     * It iteratively closes each warehouse and checks if the solution improves.
     */
    private void performGreedy(){
        for(int i = 0; i < warehouseList.size(); i++){
            this.currentSolution.set(i, false); // Close warehouse i

            float currentCost = calculateSolutionCost(this.currentSolution);

            if(currentCost < this.bestSolutionCost){
                this.bestSolution = new ArrayList<>(this.currentSolution); // Copy the list
                this.bestSolutionCost = currentCost;
            } else {
                this.currentSolution.set(i, true); // Re-open warehouse i
            }
        }
    }

    /**
     * Executes the Greedy algorithm.
     * @return AlgorithmResult containing the best solution cost and execution time.
     */
    public AlgorithmResult useGreedy(){
        long start = System.nanoTime();

        getInitialSolution();
        System.out.println("Initial solution cost: " + this.bestSolutionCost);
        performGreedy();

        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        System.out.println("Best Solution Found: " + this.bestSolutionCost);
        System.out.println("Time elapsed: " + elapsedTimeInSeconds + " seconds");

        return new AlgorithmResult(this.bestSolutionCost, elapsedTimeInSeconds);
    }

    /**
     * Calculates the cost of the current solution.
     * This includes the fixed costs of open warehouses and allocation costs to clients.
     * @param solution A list of Booleans indicating whether each warehouse is open (true) or closed (false).
     * @return Total cost of the current solution.
     */
    private float calculateSolutionCost(List<Boolean> solution) {
        float totalCost = 0;

        // Calculate the fixed cost of open warehouses
        for(int i = 0; i < solution.size(); i++){
            if(solution.get(i)){
                totalCost += warehouseList.get(i).getFixedCost();
            }
        }

        // Calculate the allocation cost of clients to open warehouses
        for (Client client : clientList) {
            float minAllocCost = Float.MAX_VALUE;
            for (int i = 0; i < solution.size(); i++) {
                if (solution.get(i)) {
                    minAllocCost = Math.min(minAllocCost, client.getAllocCosts().get(i));
                }
            }
            totalCost += minAllocCost;
        }

        return totalCost;
    }
}
