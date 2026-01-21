# Ravn-Ledger

> **Financial grade economy system preventing duplication glitches.**

Ravn-Ledger is a high-precision, double-entry bookkeeping library designed for mission-critical economy handling. It eliminates floating-point errors and ensures transactional integrity through cryptographic hashing and immutable audit logs.

## Features

### 🛡️ Precision Engineering
- **CurrencyWrapper**: Built on `BigDecimal` with strict rounding modes (HALF_UP) and fixed scale (4 decimals) to prevent fractional lost.
- **Floating-Point Free**: Zero tolerance for `double` or `float` imprecision in core logic.

### 🔒 Transaction Integrity
- **Immutable Transactions**: Once created, a transaction cannot be altered.
- **Cryptographic Hashing**: Every transaction is signed with a SHA-256 hash derived from its components (Sender, Receiver, Amount, Timestamp).
- **Tamper Evidence**: Any modification to the transaction data invalidates the hash.

### 📜 Comprehensive Auditing
- **AuditLog System**: Interfaced logging system adaptable to Files, SQL, or NoSQL.
- **Secure Persistence**: Default implementation writes secure, human-readable JSON records to flat-files.

## Installation

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>dev.ravn</groupId>
    <artifactId>ravn-ledger</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Usage Example

```java
// Initialize the Audit Log
AuditLog audit = new FileAuditLog("economy.log");

// Perform a secure transaction
UUID merchant = UUID.randomUUID();
UUID customer = UUID.randomUUID();
CurrencyWrapper price = CurrencyWrapper.of(2500.00);

Transaction tx = new Transaction(customer, merchant, price);

// Verify integrity before processing
if (tx.verifyHash()) {
    audit.log(tx);
    System.out.println("Transaction finalized: " + tx.getHash());
}
```

---
*Built for precision. Engineered for trust.*
