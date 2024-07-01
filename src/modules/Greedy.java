package modules;

import java.util.ArrayList;
import java.util.List;
public class Greedy {
    private DataContainer container;
    private List<Warehouse> warehouseList;

    private List<Client> clientList;
    private float bestSolutionCost;
    private List<Boolean> bestSolution;
    private List<Boolean> currentSolution;

    public Greedy(DataContainer container){
        this.container = container;
        this.warehouseList = this.container.getWarehouses();
        this.clientList = this.container.getClients();
        this.bestSolutionCost = 0;
        this.bestSolution = new ArrayList<>();
        this.currentSolution = new ArrayList<>();
    }

    private void getInitialSolution(){

        List<Boolean> current = new ArrayList<>();

        for(Warehouse warehouse : warehouseList){
            current.add(true);
        }
        this.currentSolution = current;
        this.bestSolution = current;
        this.bestSolutionCost = calculateSolutionCost(current);
    }

    private void performGreedy(){
        for(int i = 0; i < warehouseList.size(); i++){
            this.currentSolution.set(i, false);

            float currentCost = calculateSolutionCost(this.currentSolution);

            if(currentCost < this.bestSolutionCost){
                this.bestSolution = this.currentSolution;
                this.bestSolutionCost = currentCost;
            } else {
                this.currentSolution.set(i, true);
            }
        }
    }

    public AlgorithmResult useGreedy(){
        long start = System.nanoTime();

        getInitialSolution();
        System.out.println("Initial solution cost: " + this.bestSolutionCost);
        performGreedy();

        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        System.out.println("Best Soluction Found: " + this.bestSolutionCost);
        System.out.println("Time elapsed: " + elapsedTimeInSeconds + " seconds");


        return new AlgorithmResult(this.bestSolutionCost, elapsedTimeInSeconds);
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
