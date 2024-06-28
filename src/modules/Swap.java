package modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Swap Class
 * This class implements a swapping algorithm for solving the Uncapacitated Facility Location Problem (UFLP).
 */
public class Swap {
    private DataContainer container;
    private List<Warehouse> warehouseList;

    private List<Client> clientList;
    private float bestSolutionCost;
    private List<Boolean> bestSolution;
    private List<Boolean> currentSolution;

    private Random rand;

    /**
     * Constructor for the Swap class.
     * Initializes the Swap algorithm with the provided data container.
     * @param container The data container containing warehouses and clients.
     */
    public Swap(DataContainer container){
        this.container = container;
        this.warehouseList = this.container.getWarehouses();
        this.clientList = this.container.getClients();
        this.bestSolutionCost = 0;
        this.rand= new Random();
        this.bestSolution = new ArrayList<>();
        this.currentSolution = new ArrayList<>();
    }

    /**
     * Initializes the initial solution by randomly opening a subset of warehouses.
     * Calculates the initial solution cost and updates the best solution.
     */
    private void getInitialSolution(){
        int max = warehouseList.size();
        int min = 1;
        int range = max - min + 1;
        int numOpenWarehouses = this.rand.nextInt(range) + min;
        float currentSolution = 0;
        List<Boolean> current = new ArrayList<>();


        // Inicializar listas de instalações abertas e fechadas
        for (int i = 0; i < warehouseList.size(); i++) {
            current.add(false);

        }

        // Abrir instalações aleatórias
        for (int i = 0; i < numOpenWarehouses; i++) {
            int randIndex = this.rand.nextInt(current.size());
            current.set(randIndex, true);
        }

        this.currentSolution = current;
        this.bestSolutionCost = calculateSolutionCost(current);

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

    private List<List<Boolean>> generateNeighbours(int maxNeighbours) {
        List<List<Boolean>> neighbours = new ArrayList<>();
        List<Integer> openIndices = new ArrayList<>();
        List<Integer> closedIndices = new ArrayList<>();

        // Preenche as listas de índices abertos e fechados
        for (int i = 0; i < currentSolution.size(); i++) {
            if (currentSolution.get(i)) {
                openIndices.add(i);
            } else {
                closedIndices.add(i);
            }
        }

        // Gera vizinhos até o máximo permitido ou até esgotar as combinações possíveis
        for (int i = 0; i < Math.min(maxNeighbours, openIndices.size() * closedIndices.size()); i++) {
            // Escolhe índices aleatórios de instalações abertas e fechadas
            int openIndex = openIndices.get(rand.nextInt(openIndices.size()));
            int closedIndex = closedIndices.get(rand.nextInt(closedIndices.size()));

            // Cria uma cópia da solução atual
            List<Boolean> neighbourSolution = new ArrayList<>(this.currentSolution);

            // Troca os valores nos índices escolhidos
            neighbourSolution.set(openIndex, false);
            neighbourSolution.set(closedIndex, true);

            // Adiciona a solução vizinha à lista de vizinhos
            neighbours.add(neighbourSolution);
        }

        return neighbours;
    }

    private void localSearch(int maxIterationsWOI){
        boolean improvement = true;
        int iterationsWOI = 0;

        while(improvement && iterationsWOI < maxIterationsWOI){
            improvement = false;
            List<List<Boolean>> neighbours = generateNeighbours(10);
            for(List<Boolean> neighbour : neighbours){
                float neighbourCost = calculateSolutionCost(neighbour);
                if(neighbourCost < this.bestSolutionCost){
                    this.bestSolutionCost = neighbourCost;
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
     */
    public void useSwap(){
        long start = System.nanoTime();

        getInitialSolution();
        System.out.println("Initial solution cost: " + this.bestSolutionCost);
        localSearch(10);

        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        System.out.println("Best Solution Found: " + this.bestSolutionCost);
        System.out.println("Time elapsed: " + elapsedTimeInSeconds + " seconds");
    }
}
