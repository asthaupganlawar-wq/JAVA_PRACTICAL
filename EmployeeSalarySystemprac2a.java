
class Employee {
    void calculateSalary() {
        System.out.println("Employee Salary Details");
    }
}

class Manager extends Employee {
    @Override
    void calculateSalary() {
        System.out.println("Manager Salary: 80,000 per month");
    }
}

class Programmer extends Employee {
    @Override
    void calculateSalary() {
        System.out.println("Programmer Salary: 50,000 per month");
    }
}

public class EmployeeSalarySystemprac2a {
    public static void main(String[] args) {
        Employee employee;

        employee = new Manager();
        employee.calculateSalary();

        employee = new Programmer();
        employee.calculateSalary();
    }
}
