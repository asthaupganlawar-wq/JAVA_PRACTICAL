
class BankAccount {
    int accountNumber;
    String accountHolderName;
    double balance;

    BankAccount(int no, String name, double bal) {
        accountNumber = no;
        accountHolderName = name;
        balance = bal;
    }

    void display() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder Name: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }

    void deposit(double amount) {
        balance = balance + amount;
        System.out.println("Amount Deposited: " + amount);
        System.out.println("Balance: " + balance);
    }

    void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
            System.out.println("Amount Withdrawn: " + amount);
            System.out.println("Balance: " + balance);
        } else {
            System.out.println("Insufficient Balance");
        }
    }
}

public class BankAccountprac1b{
    public static void main(String[] args) {
        BankAccount b1 = new BankAccount(1001, "Aastha", 10000);

        b1.display();
        b1.deposit(5000);
        b1.withdraw(3000);
    }
}
