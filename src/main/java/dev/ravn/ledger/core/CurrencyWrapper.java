package dev.ravn.ledger.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.ToString;

/**
 * A wrapper around BigDecimal to ensure financial-grade precision
 * and prevent floating-point errors.
 */
@Getter
@ToString
public class CurrencyWrapper implements Comparable<CurrencyWrapper> {

    private static final int SCALE = 4; // Financial standard usually 2 or 4
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final BigDecimal value;

    public CurrencyWrapper(double value) {
        this.value = BigDecimal.valueOf(value).setScale(SCALE, ROUNDING_MODE);
    }

    public CurrencyWrapper(String value) {
        this.value = new BigDecimal(value).setScale(SCALE, ROUNDING_MODE);
    }

    public CurrencyWrapper(BigDecimal value) {
        this.value = value.setScale(SCALE, ROUNDING_MODE);
    }

    public static CurrencyWrapper of(double value) {
        return new CurrencyWrapper(value);
    }
    
    public static CurrencyWrapper of(String value) {
        return new CurrencyWrapper(value);
    }

    public static CurrencyWrapper zero() {
        return new CurrencyWrapper(BigDecimal.ZERO);
    }

    public CurrencyWrapper add(CurrencyWrapper other) {
        return new CurrencyWrapper(this.value.add(other.value));
    }

    public CurrencyWrapper subtract(CurrencyWrapper other) {
        return new CurrencyWrapper(this.value.subtract(other.value));
    }

    public CurrencyWrapper multiply(double multiplier) {
        return new CurrencyWrapper(this.value.multiply(BigDecimal.valueOf(multiplier)));
    }
    
    public boolean isPositive() {
        return this.value.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean isNegative() {
        return this.value.compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    public int compareTo(CurrencyWrapper other) {
        return this.value.compareTo(other.value);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyWrapper that = (CurrencyWrapper) o;
        return value.compareTo(that.value) == 0; 
    }

    @Override
    public int hashCode() {
        return value.stripTrailingZeros().hashCode();
    }
}
