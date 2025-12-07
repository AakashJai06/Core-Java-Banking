package bank;

public class Savings extends Account {

    public Savings(String name, String email, String accountID, String phoneNumber, double balance) {
        super(name, email, accountID, phoneNumber, balance);
    }

    @Override
    public boolean canWithdraw(double amount) {
        return balance >= amount;
    }
}
