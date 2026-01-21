# 📒 Ravn-Ledger

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Security](https://img.shields.io/badge/Security-Audit%20Ready-green?style=for-the-badge&logo=shield&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

> **"Trust, but verify."**
> The standard for economic integrity in Minecraft networks.

**Ravn-Ledger** is a transactional middleware designed to eliminate economy glitches, duplication exploits, and race conditions. It implements **Double-Entry Bookkeeping** principles to ensure that every coin created or destroyed is cryptographically accounted for.

### 🛡️ Key Features
* **Immutable Transactions:** Every transfer is hashed (SHA-256). Once recorded, logs cannot be silently altered.
* **Async Auditing:** High-throughput logging system that doesn't block the main server thread.
* **Race Condition Protection:** Atomic operations ensure money isn't lost during server lag or crashes.
* **Fraud Detection:** (Experimental) Flags suspicious rapid-fire transactions automatically.

### 💻 API Usage
```java
// Create a secure transaction
Transaction tx = Transaction.builder()
    .from(playerA)
    .to(playerB)
    .amount(new BigDecimal("5000.00"))
    .reason("AuctionHouse Sale")
    .build();

// Process with ACID guarantees
RavnLedger.getService().process(tx)
    .thenAccept(result -> {
        if (result.isSuccess()) {
            System.out.println("Transaction Hash: " + result.getHash());
        }
    });
