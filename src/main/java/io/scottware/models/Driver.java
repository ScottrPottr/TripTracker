package io.scottware.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    private String name;
    private List<Trip> trips = new ArrayList<Trip>();

    public Driver(String name) {
        this.name = name;
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    public TripSummary getTripSummary() {
        BigDecimal totalMiles = BigDecimal.ZERO;
        BigDecimal highwayMiles = BigDecimal.ZERO;
        BigDecimal speeds = BigDecimal.ZERO;
        for(Trip trip : trips) {
            totalMiles = totalMiles.add(trip.getMilesDriven());
            BigDecimal speed = trip.getSpeed();
            speeds = speeds.add(speed);
            if(speed.compareTo(BigDecimal.valueOf(50, 0)) > 0) {
                highwayMiles = highwayMiles.add(trip.getMilesDriven());
            }
        }
        BigDecimal averageSpeed = BigDecimal.ZERO;
        if(speeds.compareTo(BigDecimal.ZERO) > 0) {
            averageSpeed = speeds.divide(new BigDecimal(trips.size()), 4, BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal percentageHighwayMiles = BigDecimal.ZERO;
        if(highwayMiles.compareTo(BigDecimal.ZERO) > 0) {
            percentageHighwayMiles = highwayMiles.divide(totalMiles, 4, BigDecimal.ROUND_HALF_UP);
            percentageHighwayMiles = percentageHighwayMiles.multiply(BigDecimal.valueOf(100, 0));
        }
        return new TripSummary(name, totalMiles.setScale(0, BigDecimal.ROUND_HALF_UP), averageSpeed.setScale(0, BigDecimal.ROUND_HALF_UP), percentageHighwayMiles.setScale(0, BigDecimal.ROUND_HALF_UP));
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        if (name != null ? !name.equalsIgnoreCase(driver.name) : driver.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.toLowerCase().hashCode() : 0;
    }
}
