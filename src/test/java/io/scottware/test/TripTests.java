package io.scottware.test;

import io.scottware.CommandLineParser;
import io.scottware.models.Driver;
import io.scottware.models.Trip;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class TripTests {

    @Test
    public void test_get_speed_half_hour() {
        Trip testTrip = buildTestTrip("test", "07:15", "07:45", "17.3");

        BigDecimal expectedSpeed = new BigDecimal("34.6000");
        assertEquals("Speed should match expected", expectedSpeed, testTrip.getSpeed());
    }

    @Test
    public void test_get_speed_twenty_minutes_and_super_early() {
        Trip testTrip = buildTestTrip("test", "06:12", "06:32", "12.9");

        BigDecimal expectedSpeed = new BigDecimal("38.7039");
        assertEquals("Speed should match expected", expectedSpeed, testTrip.getSpeed());
    }

    @Test
    public void test_get_speed_after_noon() {
        Trip testTrip = buildTestTrip("test", "12:01", "13:16", "42.0");

        BigDecimal expectedSpeed = new BigDecimal("33.6000");
        assertEquals("Speed should match expected", expectedSpeed, testTrip.getSpeed());
    }

    public static Trip buildTestTrip(String driverName, String startTimeString, String stopTimeString, String milesDrivenString) {
        Driver driver = new Driver(driverName);
        LocalTime startTime = CommandLineParser.parseTime(startTimeString);
        LocalTime endTime = CommandLineParser.parseTime(stopTimeString);
        BigDecimal milesDriven = new BigDecimal(milesDrivenString);
        return new Trip(driver, startTime, endTime, milesDriven);

    }
}
