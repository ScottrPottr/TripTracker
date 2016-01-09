package io.scottware.models;

import java.math.BigDecimal;

public class TripSummary {
    private BigDecimal percentageHighwayMiles;
    private String driverName;
    private BigDecimal miles;
    private BigDecimal averageSpeed;

    public TripSummary(String driverName, BigDecimal miles, BigDecimal averageSpeed, BigDecimal percentageHighwayMiles) {
        this.driverName = driverName;
        this.miles = miles;
        this.averageSpeed = averageSpeed;
        this.percentageHighwayMiles = percentageHighwayMiles;
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

    public BigDecimal getPercentageHighwayMiles() {
        return percentageHighwayMiles;
    }

    @Override
    public String toString() {
        if(miles.compareTo(BigDecimal.ZERO) > 0) {
            return String.format("%s: %s miles @ %s mph, %s%% hw", driverName, miles.toPlainString(), averageSpeed.toPlainString(), percentageHighwayMiles.toPlainString());
        } else {
            return String.format("%s: 0 miles", driverName);
        }
    }
}
