package io.scottware.models;

import java.math.BigDecimal;

public class TripSummary {
    private String driverName;
    private BigDecimal miles;
    private BigDecimal averageSpeed;

    public TripSummary(String driverName, BigDecimal miles, BigDecimal averageSpeed) {
        this.driverName = driverName;
        this.miles = miles;
        this.averageSpeed = averageSpeed;
    }

    public BigDecimal getMiles() {
        return miles;
    }

    public String getDriverName() {
        return driverName;
    }

    public BigDecimal getAverageSpeed() {
        return averageSpeed;
    }

    @Override
    public String toString() {
        if(miles.compareTo(BigDecimal.ZERO) > 0) {
            return String.format("%s: %s miles @ %s mph", driverName, miles.toPlainString(), averageSpeed.toPlainString());
        } else {
            return String.format("%s: 0 miles", driverName);
        }

    }
}
