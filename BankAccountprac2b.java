
class Bank {
    void interestRate() {
        System.out.println("Bank Interest Rate");
    }
}

class SBI extends Bank {
    @Override
    void interestRate() {
        System.out.println("SBI Interest Rate: 6.5%");
    }
}

class HDFC extends Bank {
    @Override
    void interestRate() {
        System.out.println("HDFC Interest Rate: 7.0%");
    }
}

class ICICI extends Bank {
    @Override
    void interestRate() {
        System.out.println("ICICI Interest Rate: 6.8%");
    }
}

public class BankAccountprac2b{
    public static void main(String[] args) {
        Bank bank;

        bank = new SBI();
        bank.interestRate();

        bank = new HDFC();
        bank.interestRate();

        bank = new ICICI();
        bank.interestRate();
    }
}
