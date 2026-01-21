package dev.ravn.ledger;

import dev.ravn.ledger.audit.AuditLog;
import dev.ravn.ledger.audit.FileAuditLog;
import dev.ravn.ledger.core.CurrencyWrapper;
import dev.ravn.ledger.core.Transaction;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Ravn-Ledger System...");

        // Setup
        AuditLog auditLog = new FileAuditLog("ledger.log");

        UUID sender = UUID.randomUUID();
        UUID receiver = UUID.randomUUID();
        CurrencyWrapper amount = CurrencyWrapper.of(150.50);

        System.out.println("Creating Transaction...");
        System.out.println("Sender: " + sender);
        System.out.println("Receiver: " + receiver);
        System.out.println("Amount: " + amount);

        Transaction transaction = new Transaction(sender, receiver, amount);

        System.out.println("Transaction Created with Hash: " + transaction.getHash());
        System.out.println("Verifying Hash Integrity: " + transaction.verifyHash());

        System.out.println("Logging to Audit System...");
        auditLog.log(transaction);

        System.out.println("Done. Check ledger.log for output.");
    }
}
