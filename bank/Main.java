package bank;

import java.util.Scanner;

public class Main{
    static Scanner sc = new Scanner(System.in);
    static BankService bank = new BankService();
    public static void printMenu(){
        System.out.println("============================================================\n\n                 CONSOLE BANKING SYSTEM \n\n============================================================");
        System.out.println("[1] Create New bank.Account \n" +
                "\n" +
                "[2] Deposit Money \n" +
                "\n" +
                "[3] Withdraw Money \n" +
                "\n" +
                "[4] Transfer Funds (Instant) \n" +
                "\n" +
                "[5] Schedule Transfer (Future/Async) \n" +
                "\n" +
                "[6] View bank.Account Details \n" +
                "\n" +
                "[7] Reporting Dashboard \n" +
                "\n" +
                "[8] Run Concurrency Stress Test (Simulation) \n" +
                "\n" +
                "[9] Exit \n\n");
        System.out.println("============================================================");
        System.out.println("\nEnter your choice: ");
    }
    public static void handleChoice(String choice) {
        switch (choice) {
            case "1":
                System.out.println("Type (Savings/Current) : ");
                String type = sc.nextLine();
                System.out.println("Name : ");
                String name = sc.nextLine();
                System.out.println("Email : ");
                String email = sc.nextLine();
                System.out.println("Phone Number : ");
                String phoneNumber = sc.nextLine();

                if(phoneNumber.length()!=10) throw new IllegalArgumentException("Invalid Phone Number");

                if(!email.contains("@")) throw new IllegalArgumentException("Invalid Email");

                System.out.println("Initial Balance : ");
                double balance = sc.nextDouble();
                double od = 0;
                if(type.equalsIgnoreCase("Current")){
                    System.out.println("Overdraft Limit : ");
                    od = sc.nextDouble();
                }
                Account acc = bank.createAccount(type,name,email,phoneNumber,balance,od);
                System.out.println("Created Account : " + acc.getAccountId());
                break;

            case "2":
                System.out.println("Account ID : ");
                String id = sc.nextLine();
                System.out.println("Amount to be deposited : ");
                double amount = sc.nextDouble();
                bank.deposit(id,amount);
                System.out.println("SUCCESS");
                break;
            case "3":
                System.out.println("Account ID : ");
                id = sc.nextLine();
                System.out.println("Amount to withdraw : ");
                amount = sc.nextDouble();
                bank.withdraw(id,amount);
                System.out.println("SUCCESS");
                break;
            case "4":
                System.out.println("From ID : ");
                String from = sc.nextLine();
                System.out.println("To ID : ");
                String to = sc.nextLine();
                System.out.println("Amount : ");
                amount = sc.nextDouble();
                bank.transfer(from,to,amount);
                break;
            case "5":
                System.out.println("From ID : ");
                from = sc.nextLine();
                System.out.println("To ID : ");
                to = sc.nextLine();
                System.out.println("Amount : ");
                amount = sc.nextDouble();
                System.out.println("Delay (in seconds) : ");
                int delay = sc.nextInt();
                BankUtils.scheduleTransfer(bank,from,to,amount,delay);
                System.out.println("Scheduled.");
                break;
            case "6":
                System.out.println("Account ID : ");
                id = sc.nextLine();
                acc = bank.getAccount(id);
                System.out.println(acc);
                break;
            case "7":
                System.out.println("");
                break;
            case "8":
                BankUtils.runStressTest(bank);
                break;
            case "9":   System.exit(0);break;
            default:
                System.out.println("Invalide Option");
        }
    }
    public static void main(String args[]){
        BankUtils.startInterestScheduler(bank);

        while(true){
            printMenu();
            String choice = sc.nextLine();
            handleChoice(choice);
        }
    }
}
