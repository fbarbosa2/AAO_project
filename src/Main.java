import modules.fileReader;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        fileReader reader = new fileReader();
        String filePath = "C:\\Users\\Francisco\\Desktop\\AAO_project\\src\\FicheirosTeste\\M\\Kcapmo1.txt";
        try {
            long start = System.nanoTime();
            reader.readFile(filePath);
            long end = System.nanoTime();
            long elapsedTime = end - start;
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        }

    }

}

