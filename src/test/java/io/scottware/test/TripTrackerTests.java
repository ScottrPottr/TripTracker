package io.scottware.test;

import io.scottware.*;
import io.scottware.interfaces.ITripTracker;
import io.scottware.models.Driver;
import io.scottware.models.Trip;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class TripTrackerTests {

    private ITripTracker tripTracker;

    @Before
    public void before() {
        tripTracker = new InMemoryTripTracker();
    }

    @Test
    public void drivers_unique() {
        tripTracker.registerDriver(new Driver("Ilya"));
        tripTracker.registerDriver(new Driver("Ilya"));

        String report = tripTracker.generateReport();
        assertEquals("Drivers should be unique based on name.", "Ilya: 0 miles", report);
    }

    @Test
    public void logging_trip_registers_driver() {
        tripTracker.logTrip(TripTests.buildTestTrip("Scott", "10:35", "14:15", "200"));

        String report = tripTracker.generateReport();
        assertEquals("Logging trips registers driver is not done already", "Scott: 200 miles @ 55 mph", report);
    }

    @Test
    public void discards_trips_under_lower_threshold() {
        Driver driver = new Driver("Scott");
        LocalTime startTime = CommandLineParser.parseTime("10:35");
        LocalTime stopTime = CommandLineParser.parseTime("14:17");
        BigDecimal miles = new BigDecimal("10");

        tripTracker.registerDriver(driver);
        tripTracker.logTrip(new Trip(driver, startTime, stopTime, miles));
        String report = tripTracker.generateReport();

        assertEquals("Should discard trips under threshold.", "Scott: 0 miles", report);
    }

    @Test
    public void discards_trips_over_higher_threshold() {
        Driver driver = new Driver("Scott");
        Trip trip = TripTests.buildTestTrip("Scott", "10:35", "14:17", "1000");

        tripTracker.registerDriver(driver);
        tripTracker.logTrip(trip);
        String report = tripTracker.generateReport();

        assertEquals("Should discard trips over threshold.", "Scott: 0 miles", report);
    }

    @Test
    public void generates_report_sorted_miles_driven_desc() {
        Driver driver = new Driver("Dan");
        tripTracker.registerDriver(driver);

        Trip trip = TripTests.buildTestTrip("Dan", "07:15", "07:45", "20.3");
        tripTracker.logTrip(trip);

        trip = TripTests.buildTestTrip("Dan", "06:12", "06:32", "22.9");
        tripTracker.logTrip(trip);

        driver = new Driver("Alex");
        tripTracker.registerDriver(driver);
        trip = TripTests.buildTestTrip("Alex", "12:01", "13:16", "42.0");
        tripTracker.logTrip(trip);

        driver = new Driver("Ilya");
        tripTracker.registerDriver(driver);

        String report = tripTracker.generateReport();
        String expectedReport = "Dan: 43 miles @ 55 mph\n" +
                "Alex: 42 miles @ 34 mph\n" +
                "Ilya: 0 miles";

        assertEquals("Report should order trips by miles driven desc", expectedReport, report);
    }


    @Test
    public void generates_report_sorted_miles_driven_desc_and_alpha_same_mileage() {
        Driver driver = new Driver("Dan");
        tripTracker.registerDriver(driver);

        Trip trip = TripTests.buildTestTrip("Dan", "07:15", "07:45", "20.3");
        tripTracker.logTrip(trip);

        trip = TripTests.buildTestTrip("Dan", "06:12", "06:32", "22.9");
        tripTracker.logTrip(trip);

        driver = new Driver("Alex");
        tripTracker.registerDriver(driver);
        trip = TripTests.buildTestTrip("Alex", "12:01", "13:16", "42.0");
        tripTracker.logTrip(trip);

        driver = new Driver("Ilya");
        tripTracker.registerDriver(driver);

        driver = new Driver("Scott");
        tripTracker.registerDriver(driver);
        trip = TripTests.buildTestTrip("Scott", "12:01", "13:16", "42.3");
        tripTracker.logTrip(trip);

        String report = tripTracker.generateReport();
        String expectedReport = "Dan: 43 miles @ 55 mph\n" +
                "Alex: 42 miles @ 34 mph\n" +
                "Scott: 42 miles @ 34 mph\n" +
                "Ilya: 0 miles";

        assertEquals("Report should order trips by miles driven desc, " +
                "and then alphabetically if two drivers have the same mileage", expectedReport, report);
    }

}
