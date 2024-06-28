import modules.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        fileReader reader = new fileReader();
        Scanner scan = new Scanner(System.in);
        String filePath = "C:\\Users\\Francisco\\Desktop\\AAO_project\\src\\FicheirosTeste\\ORLIB\\ORLIB-uncap\\100\\cap101.txt";
        try {

            DataContainer data = reader.readFile(filePath);
            System.out.print("1-Swap\n2-Switch\n0-Sair\nOpcao: ");
            int op = Integer.parseInt(scan.nextLine());

            while(op != 0) {
                switch (op) {
                    case 1:
                        Swap swapAlg = new Swap(data);
                        swapAlg.useSwap();
                        break;
                    case 2:
                        Switch switchAlg = new Switch(data);
                        switchAlg.useSwitch();
                        break;

                    case 0:
                        break;
                    default:
                        System.out.println("Não é uma opcao");
                        break;
                }
                break;
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        }

    }

}

