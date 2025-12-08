package bank;


public class BankUtils {

    public static void startInterestScheduler(BankService bank) {
        Thread interestThread = new Thread(() -> {
            while(true){
                try{
                Thread.sleep(10000);
                double dailyRate = 0.05 / 365.0;

            for(Account acc : bank.getAllAccounts()){
                if(acc instanceof Savings){
                synchronized (acc){
                    double interest = acc.getBalance() * dailyRate;
                    if (interest > 0){
                        acc.deposit(interest);
                        BankLogger.log(acc.getAccountId(), "INTEREST", interest, "SUCCESS");
                    }
                    }
                }
                }
            }catch(InterruptedException e){ break; }
            }
        });
        interestThread.setDaemon(true);
        interestThread.start();
    }

    public static void scheduleTransfer(BankService bank, String from, String to, double amt, int delay) {
        new Thread(() -> {
            try{
                Thread.sleep(delay * 1000L);
                System.out.println("\n[System]: Executing Scheduled Transfer...");
                bank.transfer(from, to, amt);
                System.out.println("[System]: Transfer Done.");
            }catch(Exception e){
                System.out.println("[System]: Scheduled Transfer Failed: " + e.getMessage());
            }
        }).start();
    }

    public static void runStressTest(BankService bank) {
        System.out.println("--- Starting Stress Test ---");
        Account testAcc = bank.createAccount("Savings", "TestUser", "test@test.com", "7012992145", 0,0);
        String id = testAcc.getAccountId();

        for(int i = 0; i < 20; i++){
            boolean isDeposit = (i % 2 == 0);
            Thread thread = new Thread(() -> {
                    if(isDeposit) bank.deposit(id, 10);
                    else bank.withdraw(id, 10);
            });
            thread.start();
        }

        System.out.println("\nStress Test Finished.");
        System.out.println("Balance:   " + testAcc.getBalance());
    }
}
