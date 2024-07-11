import modules.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class that contains the main method to run the application.
 */
public class Main {

    /**
     * Main method that executes the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        fileReader reader = new fileReader();
        Scanner scan = new Scanner(System.in);
        // Change this variable to read all files in a directory (true) or only a single file (false)
        boolean readAllFiles = true;

        List<String> filePaths = new ArrayList<>();

        if (readAllFiles) {
            String dirPath = "C:\\Users\\Francisco\\Desktop\\AAO_project\\src\\FicheirosTeste\\M";
            filePaths = getFilePaths(dirPath);
        } else {
            String filePath = "C:\\Users\\Francisco\\Desktop\\AAO_project\\src\\FicheirosTeste\\M\\Kcapmp1.txt";
            filePaths.add(filePath);
        }

        // List to store the results of the algorithms
        List<String> results = new ArrayList<>();

        System.out.print("1-Swap Normal\n2-Improved Swap\n3-Switch\n4-Greedy\n0-Exit\nOption: ");
        int op = Integer.parseInt(scan.nextLine());

        if (op == 0) {
            System.out.println("Exiting...");
            return;
        }

        // Process each file in the list of file paths
        for (String filePath : filePaths) {
            try {
                // Reads the file and stores the data
                DataContainer data = reader.readFile(filePath);
                AlgorithmResult result = null;

                switch (op) {
                    case 1:
                        Swap swapAlg = new Swap(data);
                        result = swapAlg.useSwap();
                        break;
                    case 2:
                        ImprovSwap improvSwapAlg = new ImprovSwap(data);
                        result = improvSwapAlg.useSwap();
                        break;
                    case 3:
                        Switch switchAlg = new Switch(data);
                        result = switchAlg.useSwitch();
                        break;
                    case 4:
                        Greedy greedy = new Greedy(data);
                        result = greedy.useGreedy();
                        break;
                    default:
                        System.out.println("Not an option");
                        continue;
                }

                // If a result is obtained, add it to the results list
                if (result != null) {
                    String fileName = new File(filePath).getName();
                    results.add("Results for " + fileName + ": " + result);
                }

            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + filePath);
            }
        }

        writeResultsToFile(results, "C:\\Users\\Francisco\\Desktop\\AAO_project\\results.txt");
    }

    /**
     * Gets the file paths of all text files in a directory.
     *
     * @param dirPath The directory path.
     * @return A list of file paths.
     */
    private static List<String> getFilePaths(String dirPath) {
        List<String> filePaths = new ArrayList<>();
        File dir = new File(dirPath);
        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                filePaths.add(file.getAbsolutePath());
            }
        }
        return filePaths;
    }

    /**
     * Writes the results to a file.
     *
     * @param results The list of results.
     * @param outputPath The output file path.
     */
    private static void writeResultsToFile(List<String> results, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            for (String result : results) {
                writer.write(result + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + outputPath);
        }
    }
}
