package io.scottware.models;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

public class Trip {
    private Driver driver;
    private LocalTime startTime;
    private LocalTime stopTime;
    private BigDecimal milesDriven;
    private BigDecimal speed;

    public Trip(Driver driver, LocalTime startTime, LocalTime stopTime, BigDecimal milesDriven) {
        this.driver = driver;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.milesDriven = milesDriven;
    }

    public BigDecimal getSpeed() {
        if(speed == null && milesDriven != null) {
            Duration duration = Duration.between(startTime, stopTime);
            long minutes = duration.toMinutes();
            BigDecimal hours = new BigDecimal(minutes).divide(new BigDecimal(60), 4, BigDecimal.ROUND_HALF_UP);

            speed = milesDriven.divide(hours, 4, BigDecimal.ROUND_HALF_UP);
        }
        return speed;
    }

    public Driver getDriver() {
        return driver;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getStopTime() {
        return stopTime;
    }

    public BigDecimal getMilesDriven() {
        return milesDriven;
    }
}
