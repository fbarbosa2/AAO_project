package modules;

/**
 * AlgorithmResult class represents a container with the result of an algorithm execution.
 * It stores the best solution cost and the elapsed time.
 */
public class AlgorithmResult {
    private final double bestSolutionCost; // Best solution cost
    private final double elapsedTimeInSeconds; // Elapsed time in seconds

    /**
     * Constructor to initialize AlgorithmResult with the best solution cost and elapsed time.
     *
     * @param bestSolutionCost Best solution cost obtained from the algorithm.
     * @param elapsedTimeInSeconds Elapsed time in seconds taken by the algorithm.
     */
    public AlgorithmResult(double bestSolutionCost, double elapsedTimeInSeconds) {
        this.bestSolutionCost = bestSolutionCost;
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
    }

    /**
     * Getter for retrieving the best solution cost.
     *
     * @return Best solution cost.
     */
    public double getBestSolutionCost() {
        return bestSolutionCost;
    }

    /**
     * Getter for retrieving the elapsed time in seconds.
     *
     * @return Elapsed time in seconds.
     */
    public double getElapsedTimeInSeconds() {
        return elapsedTimeInSeconds;
    }

    /**
     * Overrides toString() method to provide a string representation of the AlgorithmResult object.
     * Formats the result as "Best Solution Cost: <bestSolutionCost>, Time Elapsed: <elapsedTimeInSeconds> seconds".
     *
     * @return String representation of the AlgorithmResult object.
     */
    @Override
    public String toString() {
        return "Best Solution Cost: " + bestSolutionCost + ", Time Elapsed: " + elapsedTimeInSeconds + " seconds";
    }
}
