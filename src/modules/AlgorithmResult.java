package modules;

public class AlgorithmResult {
    private final double bestSolutionCost;
    private final double elapsedTimeInSeconds;

    public AlgorithmResult(double bestSolutionCost, double elapsedTimeInSeconds) {
        this.bestSolutionCost = bestSolutionCost;
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
    }

    public double getBestSolutionCost() {
        return bestSolutionCost;
    }

    public double getElapsedTimeInSeconds() {
        return elapsedTimeInSeconds;
    }

    @Override
    public String toString() {
        return "Best Solution Cost: " + bestSolutionCost + ", Time Elapsed: " + elapsedTimeInSeconds + " seconds";
    }
}
