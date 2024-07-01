import modules.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        fileReader reader = new fileReader();
        Scanner scan = new Scanner(System.in);
        String dirPath = "C:\\Users\\Francisco\\Desktop\\AAO_project\\src\\FicheirosTeste\\ORLIB\\ORLIB-uncap\\a-c";

        List<String> filePaths = getFilePaths(dirPath);
        List<String> results = new ArrayList<>();

        // Mostrar o menu uma vez e escolher o algoritmo
        System.out.print("1-Swap\n2-Switch\n3-Greedy\n0-Sair\nOpcao: ");
        int op = Integer.parseInt(scan.nextLine());

        if (op == 0) {
            System.out.println("Exiting...");
            return;
        }

        for (String filePath : filePaths) {
            try {
                DataContainer data = reader.readFile(filePath);
                AlgorithmResult result = null;

                switch (op) {
                    case 1:
                        Swap swapAlg = new Swap(data);
                        result = swapAlg.useSwap();
                        break;
                    case 2:
                        Switch switchAlg = new Switch(data);
                        result = switchAlg.useSwitch();
                        break;
                    case 3:
                        Greedy greedy = new Greedy(data);
                        result = greedy.useGreedy();
                        break;
                    default:
                        System.out.println("Não é uma opção");
                        continue;
                }

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

