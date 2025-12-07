package bank;

import java.util.Comparator;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static BankService bank = new BankService();

    public static void printMenu() {
        System.out.println("============================================================\n\n                 CONSOLE BANKING SYSTEM \n\n============================================================");
        System.out.println("[1] Create New Account");
        System.out.println("[2] Deposit Money");
        System.out.println("[3] Withdraw Money");
        System.out.println("[4] Transfer Funds (Instant)");
        System.out.println("[5] Schedule Transfer (Future/Async)");
        System.out.println("[6] View Account Details");
        System.out.println("[7] Reporting Dashboard");
        System.out.println("[8] Run Concurrency Stress Test (Simulation)");
        System.out.println("[9] Exit");
        System.out.println("============================================================");
        System.out.print("\nEnter your choice: ");
    }

    public static void handleChoice(String choice) {
        switch (choice) {
            case "1":
                System.out.print("Type (Savings/Current) : ");
                String type = sc.nextLine();
                System.out.print("Name : ");
                String name = sc.nextLine();
                System.out.print("Email : ");
                String email = sc.nextLine();
                System.out.print("Phone Number : ");
                String phoneNumber = sc.nextLine();

                // Validation logic from your original code
                if (phoneNumber.length() != 10) throw new IllegalArgumentException("Invalid Phone Number (Must be 10 digits)");
                if (!email.contains("@")) throw new IllegalArgumentException("Invalid Email");

                System.out.print("Initial Balance : ");
                // FIX: Use parseDouble(nextLine()) to avoid scanner buffer issues
                double balance = Double.parseDouble(sc.nextLine());
                double od = 0;
                if (type.equalsIgnoreCase("Current")) {
                    System.out.print("Overdraft Limit : ");
                    od = Double.parseDouble(sc.nextLine());
                }
                Account acc = bank.createAccount(type, name, email, phoneNumber, balance, od);
                System.out.println("Created Account : " + acc.getAccountId());
                break;

            case "2":
                System.out.print("Account ID : ");
                String id = sc.nextLine();
                System.out.print("Amount to be deposited : ");
                double amount = Double.parseDouble(sc.nextLine());
                bank.deposit(id, amount);
                System.out.println("SUCCESS");
                break;

            case "3":
                System.out.print("Account ID : ");
                id = sc.nextLine();
                System.out.print("Amount to withdraw : ");
                amount = Double.parseDouble(sc.nextLine());
                bank.withdraw(id, amount);
                System.out.println("SUCCESS");
                break;

            case "4":
                System.out.print("From ID : ");
                String from = sc.nextLine();
                System.out.print("To ID : ");
                String to = sc.nextLine();
                System.out.print("Amount : ");
                amount = Double.parseDouble(sc.nextLine());
                bank.transfer(from, to, amount);
                System.out.println("Transfer Successful");
                break;

            case "5":
                System.out.print("From ID : ");
                from = sc.nextLine();
                System.out.print("To ID : ");
                to = sc.nextLine();
                System.out.print("Amount : ");
                amount = Double.parseDouble(sc.nextLine());
                System.out.print("Delay (in seconds) : ");
                int delay = Integer.parseInt(sc.nextLine());
                BankUtils.scheduleTransfer(bank, from, to, amount, delay);
                System.out.println("Scheduled.");
                break;

            case "6":
                System.out.print("Account ID : ");
                id = sc.nextLine();
                acc = bank.getAccount(id);
                if (acc != null) {
                    System.out.println(acc);
                } else {
                    System.out.println("Account not found.");
                }
                break;

            case "7":
                // Implemented Reporting Dashboard
                System.out.println("\n--- Reporting Dashboard ---");
                System.out.println("1. List Accounts > Balance");
                System.out.println("2. Sort Accounts by Balance");
                System.out.println("3. Count Overdraft Accounts");
                System.out.print("Choose: ");
                String reportChoice = sc.nextLine();
                
                if (reportChoice.equals("1")) {
                    System.out.print("Enter Minimum Balance: ");
                    double minBal = Double.parseDouble(sc.nextLine());
                    bank.getAllAccounts().stream()
                        .filter(a -> a.getBalance() > minBal)
                        .forEach(System.out::println);
                } else if (reportChoice.equals("2")) {
                    bank.getAllAccounts().stream()
                        .sorted(Comparator.comparingDouble(Account::getBalance))
                        .forEach(System.out::println);
                } else if (reportChoice.equals("3")) {
                    long count = bank.getAllAccounts().stream()
                        .filter(a -> a.getBalance() < 0)
                        .count();
                    System.out.println("Total Accounts in Overdraft: " + count);
                }
                break;

            case "8":
                BankUtils.runStressTest(bank);
                break;

            case "9":
                System.out.println("Exiting...");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid Option");
        }
    }

    public static void main(String args[]) {
        BankUtils.startInterestScheduler(bank);

        while (true) {
            try {
                printMenu();
                String choice = sc.nextLine();
                handleChoice(choice);
            } catch (Exception e) {
                System.out.println("\n[ERROR]: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }
}
