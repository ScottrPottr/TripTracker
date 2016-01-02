package io.scottware;

import io.scottware.interfaces.ITripTracker;
import io.scottware.models.Driver;
import io.scottware.models.Trip;
import io.scottware.models.TripSummary;

import java.math.BigDecimal;
import java.util.*;

public class InMemoryTripTracker implements ITripTracker {

    public static final BigDecimal MINIMUM_SPEED_TO_LOG = BigDecimal.valueOf(5, 0);
    public static final BigDecimal MAXIMUM_SPEED_TO_LOG = BigDecimal.valueOf(100, 0);

    Map<String, Driver> drivers = new TreeMap<String, Driver>(String.CASE_INSENSITIVE_ORDER);

    @Override
    public void registerDriver(Driver driver) {
        if(!drivers.containsKey(driver.getName())) {
            drivers.put(driver.getName(), driver);
        }
    }

    @Override
    public void logTrip(Trip trip) {
        BigDecimal speed = trip.getSpeed();
        if(speed != null && speed.compareTo(MINIMUM_SPEED_TO_LOG) > 0 && speed.compareTo(MAXIMUM_SPEED_TO_LOG) < 0) {
            Driver driver = trip.getDriver();
            registerDriver(driver);
            driver = drivers.get(driver.getName());
            driver.addTrip(trip);
        }
    }

    @Override
    public String generateReport() {
        List<TripSummary> tripSummaries = new ArrayList<TripSummary>();

        for(Driver driver : drivers.values()) {
            tripSummaries.add(driver.getTripSummary());
        }

        Collections.sort(tripSummaries, new Comparator<TripSummary>() {
            @Override
            public int compare(TripSummary t1, TripSummary t2) {
                return t2.getMiles().subtract(t1.getMiles()).intValue();
            }
        });

        StringBuilder stringBuilder = new StringBuilder();
        for(TripSummary tripSummary : tripSummaries) {
            stringBuilder.append(tripSummary.toString())
                    .append("\n");
        }
        return stringBuilder.toString().trim();
    }
}
