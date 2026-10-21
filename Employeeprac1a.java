
class Employee {
    int employeeId;
    String employeeName;
    double salary;

    Employee(int id, String name, double sal) {
        employeeId = id;
        employeeName = name;
        salary = sal;
    }

    void display() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Employee Name: " + employeeName);
        System.out.println("Salary: " + salary);
    }
}

public class Employeeprac1a {
    public static void main(String[] args) {

        Employee e1 = new Employee(101, "Aastha", 50000);
        Employee e2 = new Employee(102, "Shruti", 45000);

        e1.display();
        System.out.println();

        e2.display();
    }
}
