package modules;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an algorithm for warehouse switching optimization.
 * Uses local search to find the best solution for opening warehouses.
 */
public class Switch {

    private DataContainer container; // Data container holding warehouses and clients
    private List<Warehouse> warehouseList; // List of warehouses
    private List<Client> clientList; // List of clients
    private float bestSolutionCost; // Cost of the best solution found
    private List<Boolean> bestSolution; // Boolean list representing the best solution
    private List<Boolean> currentSolution; // Boolean list representing the current solution

    /**
     * Constructor to initialize the Switch algorithm with a data container.
     *
     * @param container The data container containing warehouses and clients.
     */
    public Switch(DataContainer container) {
        this.container = container;
        this.warehouseList = this.container.getWarehouses(); // Get warehouses from data container
        this.clientList = this.container.getClients(); // Get clients from data container
        this.bestSolutionCost = 0; // Initialize best solution cost
        this.bestSolution = new ArrayList<>(); // Initialize best solution list
        this.currentSolution = new ArrayList<>(); // Initialize current solution list
    }

    /**
     * Initializes the current solution by opening warehouses with even indices.
     * Sets the initial solution as the best solution.
     */
    private void getInitialSolution() {
        List<Boolean> current = new ArrayList<>();

        // Initialize list of warehouse states
        for (int i = 0; i < warehouseList.size(); i++) {
            current.add(false); // Initially all warehouses are closed
        }

        // Open warehouses with even indices
        for (int i = 0; i < warehouseList.size(); i++) {
            if (i % 2 == 0) {
                current.set(i, true); // Set warehouse as open
            }
        }

        // Set initial solution and best solution
        this.currentSolution = current;
        this.bestSolution = current;
        this.bestSolutionCost = calculateSolutionCost(current);
    }

    /**
     * Generates neighboring solutions by toggling the state of each warehouse.
     *
     * @return List of neighboring solutions.
     */
    private List<List<Boolean>> generateNeighbours() {
        List<List<Boolean>> neighbours = new ArrayList<>();
        for (int i = 0; i < warehouseList.size(); i++) {
            List<Boolean> neighbourSolution = new ArrayList<>(this.currentSolution);
            if (neighbourSolution.get(i)) {
                neighbourSolution.set(i, false); // Toggle warehouse state
                // Check if at least one warehouse remains open
                if (neighbourSolution.contains(true)) {
                    neighbours.add(neighbourSolution);
                }
            }
        }
        return neighbours;
    }

    /**
     * Performs local search to find an improved solution.
     * Updates the best solution if an improvement is found.
     */
    private void localSearch() {
        boolean improvement = true;

        while (improvement) {
            improvement = false;
            List<List<Boolean>> neighbours = generateNeighbours();

            // Iterate over each neighboring solution
            for (List<Boolean> neighbour : neighbours) {
                float neighbourCost = calculateSolutionCost(neighbour);
                if (neighbourCost < this.bestSolutionCost) {
                    this.bestSolutionCost = neighbourCost; // Update best solution cost
                    this.bestSolution = neighbour; // Update best solution
                    improvement = true; // Mark improvement
                }
            }
            this.currentSolution = this.bestSolution; // Set current solution to best solution
        }
    }

    /**
     * Executes the Switch algorithm.
     *
     * @return AlgorithmResult containing the best solution cost and execution time.
     */
    public AlgorithmResult useSwitch() {
        long start = System.nanoTime();

        getInitialSolution(); // Initialize the current solution
        System.out.println("Initial solution cost: " + this.bestSolutionCost);
        localSearch(); // Perform local search to find the best solution

        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        System.out.println("Best Solution Found: " + this.bestSolutionCost);
        System.out.println("Time elapsed: " + elapsedTimeInSeconds + " seconds");

        return new AlgorithmResult(this.bestSolutionCost, elapsedTimeInSeconds); // Return the result
    }

    /**
     * Calculates the cost of a given solution.
     * Includes fixed costs of open warehouses and allocation costs to clients.
     *
     * @param solution The solution to calculate the cost for.
     * @return Total cost of the solution.
     */
    private float calculateSolutionCost(List<Boolean> solution) {
        float totalCost = 0;

        // Calculate fixed costs of open warehouses
        for (int i = 0; i < solution.size(); i++) {
            if (solution.get(i)) {
                totalCost += warehouseList.get(i).getFixedCost();
            }
        }

        // Calculate allocation costs of clients to open warehouses
        for (Client client : clientList) {
            float minAllocCost = Float.MAX_VALUE;
            for (int i = 0; i < solution.size(); i++) {
                if (solution.get(i)) {
                    minAllocCost = Math.min(minAllocCost, client.getAllocCosts().get(i));
                }
            }
            totalCost += minAllocCost;
        }

        return totalCost; // Return the total cost of the solution
    }
}
