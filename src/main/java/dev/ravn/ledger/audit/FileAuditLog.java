package dev.ravn.ledger.audit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.ravn.ledger.core.Transaction;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

public class FileAuditLog implements AuditLog {

    private final String filePath;
    private final Gson gson;

    public FileAuditLog(String filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void log(Transaction transaction) {
        try (FileWriter fileWriter = new FileWriter(filePath, true);
                PrintWriter printWriter = new PrintWriter(fileWriter)) {

            String json = gson.toJson(transaction);
            printWriter
                    .println("--- TRANSACTION RECORD [" + Instant.ofEpochMilli(transaction.getTimestamp()) + "] ---");
            printWriter.println(json);
            printWriter.println("---------------------------------------------");

        } catch (IOException e) {
            System.err.println("Failed to write to audit log: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
