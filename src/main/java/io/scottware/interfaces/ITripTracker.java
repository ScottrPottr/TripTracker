package io.scottware.interfaces;

import io.scottware.models.Driver;
import io.scottware.models.Trip;

public interface ITripTracker {
    void registerDriver(Driver driver);
    void logTrip(Trip trip);
    String generateReport();
}
