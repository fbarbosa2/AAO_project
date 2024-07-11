package modules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * ImprovSwap Class
 * This class implements an improved swapping algorithm for solving the Uncapacitated Facility Location Problem (UFLP).
 */
public class ImprovSwap {
    private DataContainer container; // Data container holding warehouses and clients
    private List<Warehouse> warehouseList; // List of warehouses
    private List<Client> clientList; // List of clients
    private float bestSolutionCost; // Cost of the best solution found
    private List<Boolean> bestSolution; // Boolean list representing the best solution
    private List<Boolean> currentSolution; // Boolean list representing the current solution

    private Random rand; // Random number generator

    /**
     * Constructor for the ImprovSwap class.
     * Initializes the ImprovSwap algorithm with the provided data container.
     * @param container The data container containing warehouses and clients.
     */
    public ImprovSwap(DataContainer container){
        this.container = container;
        this.warehouseList = this.container.getWarehouses(); // Get warehouses from data container
        this.clientList = this.container.getClients(); // Get clients from data container
        this.bestSolutionCost = 0; // Initialize best solution cost
        this.rand = new Random(); // Initialize random number generator
        this.bestSolution = new ArrayList<>(); // Initialize best solution list
        this.currentSolution = new ArrayList<>(); // Initialize current solution list
    }

    /**
     * Initializes the initial solution by opening a subset of warehouses with the lowest fixed costs.
     * Calculates the initial solution cost and updates the best solution.
     */
    private void getInitialSolution(int numOpenWarehouses) {

        List<Boolean> current = new ArrayList<>();

        // Initialize lists of open and closed warehouses
        for (int i = 0; i < warehouseList.size(); i++) {
            current.add(false);
        }

        // Sort warehouses by fixed cost
        List<Warehouse> sortedWarehouses = new ArrayList<>(warehouseList);
        sortedWarehouses.sort(Comparator.comparingDouble(Warehouse::getFixedCost));

        // Open the warehouses with the lowest fixed costs
        for (int i = 0; i < numOpenWarehouses && i < sortedWarehouses.size(); i++) {
            int index = warehouseList.indexOf(sortedWarehouses.get(i));
            current.set(index, true);
        }

        this.currentSolution = current;
        this.bestSolutionCost = calculateSolutionCost(current);
    }

    /**
     * Calculates the current solution cost.
     * This includes the fixed costs of open warehouses and allocation costs to clients.
     * @param solution The current solution configuration.
     * @return Total cost of the current solution.
     */
    private float calculateSolutionCost(List<Boolean> solution) {
        float totalCost = 0;

        // Calculate fixed costs of open warehouses
        for(int i = 0; i < solution.size(); i++){
            if(solution.get(i)){
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

        return totalCost;
    }

    /**
     * Generates neighboring solutions by swapping open and closed warehouses.
     * @param maxNeighbours Maximum number of neighboring solutions to generate.
     * @return List of neighboring solutions.
     */
    private List<List<Boolean>> generateNeighbours(int maxNeighbours) {
        List<List<Boolean>> neighbours = new ArrayList<>();
        List<Integer> openIndices = new ArrayList<>();
        List<Integer> closedIndices = new ArrayList<>();

        // Populate lists of open and closed indices
        for (int i = 0; i < currentSolution.size(); i++) {
            if (currentSolution.get(i)) {
                openIndices.add(i);
            } else {
                closedIndices.add(i);
            }
        }

        // Generate neighbours up to the maximum allowed or until all possible combinations are exhausted
        for (int i = 0; i < Math.min(maxNeighbours, openIndices.size() * closedIndices.size()); i++) {
            // Choose random indices of open and closed warehouses
            int openIndex = openIndices.get(rand.nextInt(openIndices.size()));
            int closedIndex = closedIndices.get(rand.nextInt(closedIndices.size()));


            List<Boolean> neighbourSolution = new ArrayList<>(this.currentSolution);

            // Swap values at the chosen indices
            neighbourSolution.set(openIndex, false);
            neighbourSolution.set(closedIndex, true);

            // Add the neighbouring solution to the list of neighbours
            neighbours.add(neighbourSolution);
        }

        return neighbours;
    }

    /**
     * Executes the improved swap algorithm to find the best solution.
     * Prints initial and best solution costs, and the elapsed time.
     * @return AlgorithmResult containing the best solution cost and elapsed time.
     */
    public AlgorithmResult useSwap() {
        long start = System.nanoTime();
        int numOpenWarehouses = 10; // Number of warehouses to open initially
        getInitialSolution(numOpenWarehouses);
        System.out.println("Initial solution cost: " + this.bestSolutionCost);
        localSearch(100); // Perform local search with a maximum of 10 iterations without improvement

        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        System.out.println("Best Solution Found: " + this.bestSolutionCost);
        System.out.println("Time elapsed: " + elapsedTimeInSeconds + " seconds");

        return new AlgorithmResult(this.bestSolutionCost, elapsedTimeInSeconds);
    }

    /**
     * Executes the local search phase of the algorithm.
     * Tries to improve the current solution by evaluating neighbouring solutions.
     * @param maxIterationsWOI Maximum iterations without improvement allowed in local search.
     */
    private void localSearch(int maxIterationsWOI){
        boolean improvement = true;
        int iterationsWOI = 0;

        while(improvement && iterationsWOI < maxIterationsWOI){
            improvement = false;
            List<List<Boolean>> neighbours = generateNeighbours(10); // Generate up to 10 neighbours
            for(List<Boolean> neighbour : neighbours){
                float neighbourCost = calculateSolutionCost(neighbour);
                if(neighbourCost < this.bestSolutionCost){
                    this.bestSolutionCost = neighbourCost;
                    iterationsWOI = 0; // Reset iterations without improvement
                    improvement = true;
                    this.currentSolution = new ArrayList<>(neighbour);
                } else {
                    iterationsWOI++;
                }
            }
        }
    }
}
