import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Employeeprac3a {
    public static void main(String[] args) {

        try (FileWriter writer = new FileWriter("employees.txt")) {

            writer.write("Employee ID: 101\n");
            writer.write("Name: Shruti\n");
            writer.write("Department: HR\n");
            writer.write("Salary: 50000\n\n");

            writer.write("Employee ID: 102\n");
            writer.write("Name: Akansha\n");
            writer.write("Department: IT\n");
            writer.write("Salary: 65000\n\n");

            writer.write("Employee ID: 103\n");
            writer.write("Name: Shivani\n");
            writer.write("Department: Finance\n");
            writer.write("Salary: 60000\n");

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        try (FileReader reader = new FileReader("employees.txt")) {

            int ch;
            System.out.println("Contents of employees.txt:");
            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}