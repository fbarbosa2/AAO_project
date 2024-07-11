package modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Swap Class
 * This class implements a swapping algorithm for solving the Uncapacitated Facility Location Problem (UFLP).
 */
public class Swap {

    private DataContainer container; // Data container holding warehouses and clients
    private List<Warehouse> warehouseList; // List of warehouses
    private List<Client> clientList; // List of clients
    private float bestSolutionCost; // Cost of the best solution found
    private List<Boolean> bestSolution; // Boolean list representing the best solution
    private List<Boolean> currentSolution; // Boolean list representing the current solution
    private Random rand; // Random number generator

    /**
     * Constructor for the Swap class.
     * Initializes the Swap algorithm with the provided data container.
     *
     * @param container The data container containing warehouses and clients.
     */
    public Swap(DataContainer container) {
        this.container = container;
        this.warehouseList = this.container.getWarehouses(); // Get warehouses from data container
        this.clientList = this.container.getClients(); // Get clients from data container
        this.bestSolutionCost = 0; // Initialize best solution cost
        this.rand = new Random(); // Initialize random number generator
        this.bestSolution = new ArrayList<>(); // Initialize best solution list
        this.currentSolution = new ArrayList<>(); // Initialize current solution list
    }

    /**
     * Initializes the initial solution by randomly opening a subset of warehouses.
     * Calculates the initial solution cost and updates the best solution.
     */
    private void getInitialSolution() {
        int max = warehouseList.size();
        int min = 1;
        int range = max - min + 1;
        int numOpenWarehouses = this.rand.nextInt(range) + min;
        List<Boolean> current = new ArrayList<>();

        // Initialize lists of open and closed facilities
        for (int i = 0; i < warehouseList.size(); i++) {
            current.add(false);
        }

        // Open random warehouses
        for (int i = 0; i < numOpenWarehouses; i++) {
            int randIndex = this.rand.nextInt(current.size());
            current.set(randIndex, true);
        }

        // Set current solution and calculate initial solution cost
        this.currentSolution = current;
        this.bestSolutionCost = calculateSolutionCost(current);
    }

    /**
     * Calculates the current solution cost.
     * This includes the fixed costs of open warehouses and allocation costs to clients.
     *
     * @param solution The solution to calculate the cost for.
     * @return Total cost of the current solution.
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

        return totalCost;
    }

    /**
     * Generates neighboring solutions by swapping open and closed warehouses.
     *
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

        // Generate neighbors up to the maximum allowed or until all combinations are exhausted
        for (int i = 0; i < Math.min(maxNeighbours, openIndices.size() * closedIndices.size()); i++) {
            // Choose random indices of open and closed warehouses
            int openIndex = openIndices.get(rand.nextInt(openIndices.size()));
            int closedIndex = closedIndices.get(rand.nextInt(closedIndices.size()));


            List<Boolean> neighbourSolution = new ArrayList<>(this.currentSolution);

            // Swap values at the chosen indices
            neighbourSolution.set(openIndex, false);
            neighbourSolution.set(closedIndex, true);

            // Add the neighbor solution to the list of neighbors
            neighbours.add(neighbourSolution);
        }

        return neighbours;
    }

    /**
     * Performs local search to find an improved solution.
     * Updates the best solution if an improvement is found within a maximum number of iterations without improvement.
     *
     * @param maxIterationsWOI Maximum iterations without improvement allowed.
     */
    private void localSearch(int maxIterationsWOI) {
        boolean improvement = true;
        int iterationsWOI = 0;

        while (improvement && iterationsWOI < maxIterationsWOI) {
            improvement = false;
            List<List<Boolean>> neighbours = generateNeighbours(10);

            // Iterate over each neighbor solution
            for (List<Boolean> neighbour : neighbours) {
                float neighbourCost = calculateSolutionCost(neighbour);
                if (neighbourCost < this.bestSolutionCost) {
                    this.bestSolutionCost = neighbourCost; // Update best solution cost
                    iterationsWOI = 0;
                    improvement = true;
                    this.currentSolution = new ArrayList<>(neighbour);
                } else {
                    iterationsWOI++;
                }
            }
        }
    }

    /**
     * Executes the swap algorithm to find the best solution.
     * Prints initial and best solution costs, and the elapsed time.
     *
     * @return AlgorithmResult containing the best solution cost and execution time.
     */
    public AlgorithmResult useSwap() {
        long start = System.nanoTime();

        getInitialSolution(); // Initialize the initial solution
        System.out.println("Initial solution cost: " + this.bestSolutionCost);
        localSearch(100); // Perform local search with a limit of 10 iterations without improvement

        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        System.out.println("Best Solution Found: " + this.bestSolutionCost);
        System.out.println("Time elapsed: " + elapsedTimeInSeconds + " seconds");

        return new AlgorithmResult(this.bestSolutionCost, elapsedTimeInSeconds); // Return the result
    }
}
