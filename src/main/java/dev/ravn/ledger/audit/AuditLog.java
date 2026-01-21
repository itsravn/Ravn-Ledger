package dev.ravn.ledger.audit;

import dev.ravn.ledger.core.Transaction;

public interface AuditLog {
    void log(Transaction transaction);
}
