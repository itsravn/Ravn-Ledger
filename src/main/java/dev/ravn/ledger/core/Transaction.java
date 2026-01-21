package dev.ravn.ledger.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Transaction {

    private final UUID sender;
    private final UUID receiver;
    private final CurrencyWrapper amount;
    private final long timestamp;
    private final String hash;

    public Transaction(UUID sender, UUID receiver, CurrencyWrapper amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = Instant.now().toEpochMilli();
        this.hash = calculateHash();
    }

    // Constructor for reconstruction from storage
    public Transaction(UUID sender, UUID receiver, CurrencyWrapper amount, long timestamp, String hash) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = timestamp;
        this.hash = hash;
    }

    private String calculateHash() {
        String data = sender.toString() + receiver.toString() + amount.getValue().toString() + timestamp;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean verifyHash() {
        return calculateHash().equals(this.hash);
    }
}
