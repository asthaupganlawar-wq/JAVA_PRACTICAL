
abstract class Payment {

    abstract void payAmount();

    void PaymentDetails() {
        System.out.println("Payment Details");
    }
}

class CreditCard extends Payment {

    @Override
    void payAmount() {
        System.out.println("Payment made using Credit Card");
    }
}

class UPI extends Payment {

    @Override
    void payAmount() {
        System.out.println("Payment made using UPI");
    }
}

public class PaymentSystemprac2a {

    public static void main(String[] args) {

        Payment p;

        p = new CreditCard();
        p.PaymentDetails();
        p.payAmount();

        System.out.println();

        p = new UPI();
        p.PaymentDetails();
        p.payAmount();
    }
}
