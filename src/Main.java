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
        //Change this variable for reading all the files in a dir (true) or only a single file (false)
        boolean readAllFiles = true;

        List<String> filePaths = new ArrayList<>();

        if (readAllFiles) {
            String dirPath = "C:\\Users\\Francisco\\Desktop\\AAO_project\\src\\FicheirosTeste\\M";
            filePaths = getFilePaths(dirPath);
        } else {
            String filePath = "C:\\Users\\Francisco\\Desktop\\AAO_project\\src\\FicheirosTeste\\M\\Kcapmp1.txt";
            filePaths.add(filePath);
        }

        List<String> results = new ArrayList<>();

        System.out.print("1-Swap Normal\n2-Improved Swap\n3-Switch\n4-Greedy\n0-Sair\nOpcao: ");
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
