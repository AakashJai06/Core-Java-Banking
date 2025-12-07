package bank;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class BankLogger {
    private static final String FILE_NAME = "bank_log.txt";

    public static synchronized void log(String accountId, String type, double amount, String status) {
        String timestamp = LocalDateTime.now().toString();
        String entry = String.format("[%s] [%s] [%s] [%.2f] [%s]", timestamp, accountId, type, amount, status);

        try {
            FileWriter writer = new FileWriter(FILE_NAME, true);
            writer.write(entry + "\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Log Error: " + e.getMessage());
        }
    }
}
