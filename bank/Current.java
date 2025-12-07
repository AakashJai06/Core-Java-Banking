package bank;

public class Current extends Account {
    private double overdraftLimit;
    public Current(String name, String email, String accountID, String phoneNumber, double balance,double overdraftLimit) {
        super(name, email, accountID, phoneNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean canWithdraw(double amount) {
        return (balance+overdraftLimit) >= amount;
    }
}
