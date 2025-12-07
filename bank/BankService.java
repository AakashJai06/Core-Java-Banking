package bank;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class BankService {
    // ConcurrentHashMap handles thread-safe retrieval, though we still sync on objects for updates
    private Map<String, Account> accounts = new ConcurrentHashMap<>();

    public Account createAccount(String type, String name, String email, String phoneNumber, double balance, double overdraftLimit) {
        String accountID = "ACC" + (accounts.size() + 1001);
        Account acc;

        if (type.equalsIgnoreCase("Savings")) {
            acc = new Savings(name, email, accountID, phoneNumber, balance);
        } else {
            acc = new Current(name, email, accountID, phoneNumber, balance, overdraftLimit);
        }
        accounts.put(accountID, acc);
        BankLogger.log(accountID, "CREATE", balance, "SUCCESS");
        return acc;
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    public void deposit(String id, double amount) {
        Account acc = accounts.get(id);
        if (acc == null) throw new IllegalArgumentException("Account not found");
        
        // Account.deposit is synchronized
        acc.deposit(amount);
        BankLogger.log(id, "DEPOSIT", amount, "SUCCESS");
    }

    public void withdraw(String id, double amount) {
        Account acc = accounts.get(id);
        if (acc == null) throw new IllegalArgumentException("Account not found");

        synchronized (acc) {
            if (acc.canWithdraw(amount)) {
                acc.withdraw(amount);
                BankLogger.log(id, "WITHDRAW", amount, "SUCCESS");
            } else {
                BankLogger.log(id, "WITHDRAW", amount, "FAILED");
                throw new IllegalStateException("Insufficient Funds");
            }
        }
    }

    public void transfer(String fromId, String toId, double amount) {
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from == null || to == null) throw new IllegalArgumentException("Invalid Account ID");

        // Lock Ordering to prevent Deadlock
        Account first = fromId.compareTo(toId) < 0 ? from : to;
        Account second = fromId.compareTo(toId) < 0 ? to : from;

        synchronized (first) {
            synchronized (second) {
                if (from.canWithdraw(amount)) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    BankLogger.log(fromId, "TRANSFER_OUT", amount, "SUCCESS");
                    BankLogger.log(toId, "TRANSFER_IN", amount, "SUCCESS");
                } else {
                    throw new IllegalStateException("Insufficient Funds for Transfer");
                }
            }
        }
    }
}
