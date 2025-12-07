package bank;

public abstract class Account {
    private String name;
    private String email;
    private String accountID;
    private String phoneNumber;
    protected double balance;

    public Account(String name, String email, String accountID, String phoneNumber, double balance){
        this.name = name;
        this.email = email;
        this.accountID = accountID;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public abstract boolean canWithdraw(double amount);

    public synchronized void deposit(double amount){
        this.balance += amount;
    }

    public synchronized void withdraw(double amount){
        this.balance -= amount;
    }

    public String getAccountId(){return accountID;}
    public double getBalance(){return balance;}

    public String toString(){
        return "Account ID : " + accountID + " Name : " + name + " Balance : " + balance;
    }
}
