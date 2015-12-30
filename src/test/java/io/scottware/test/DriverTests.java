package io.scottware.test;

import io.scottware.models.Driver;
import io.scottware.models.Trip;
import io.scottware.models.TripSummary;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class DriverTests {

    @Test
    public void driver_calculates_summary_correctly() {
        Driver driver = new Driver("Scott");
        Trip trip1 = TripTests.buildTestTrip("Scott", "10:35", "13:15", "176");
        driver.addTrip(trip1);
        Trip trip2 = TripTests.buildTestTrip("Scott", "16:45", "18:30", "96");
        driver.addTrip(trip2);

        TripSummary tripSummary = driver.getTripSummary();

        assertEquals("Trip summary should calculate total miles correctly",
                new BigDecimal("272"),
                tripSummary.getMiles());

        assertEquals("Trip summary should average speed correctly",
                new BigDecimal("60"),
                tripSummary.getAverageSpeed());

        assertEquals("Trip summary should have correct driver name",
                "Scott",
                tripSummary.getDriverName());

    }

}
