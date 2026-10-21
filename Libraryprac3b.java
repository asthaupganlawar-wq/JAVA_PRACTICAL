import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Libraryprac3b {
    public static void main(String[] args) {

        try (FileWriter writer = new FileWriter("books.txt")) {

            writer.write("Book ID: 101\n");
            writer.write("Book Title: Java Programming\n");
            writer.write("Author Name: James Gosling\n\n");

            writer.write("Book ID: 102\n");
            writer.write("Book Title: Data Structures\n");
            writer.write("Author Name: Mark Allen Weiss\n");

            System.out.println("Book details written successfully.");

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        try (FileReader reader = new FileReader("books.txt")) {

            System.out.println("\nContents of books.txt:");
            int ch;

            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
