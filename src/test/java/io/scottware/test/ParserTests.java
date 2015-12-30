package io.scottware.test;

import io.scottware.*;
import io.scottware.exceptions.InvalidCommandException;
import io.scottware.interfaces.IParser;
import io.scottware.models.Command;
import io.scottware.models.Driver;
import io.scottware.models.Trip;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ParserTests {

    private static IParser parser;

    @BeforeClass
    public static void before() {
        parser = new CommandLineParser();
    }

    @Test
    public void parse_driver_command() throws InvalidCommandException {
        Command command = parser.parseCommand("Driver Dan");
        assertEquals("Did not properly identify Driver command", Command.DRIVER, command);
    }

    @Test
    public void parse_trip_command() throws InvalidCommandException {
        Command command = parser.parseCommand("Trip Dan 07:15 07:45 17.3");
        assertEquals("Did not properly identify Trip command", Command.TRIP, command);
    }

    @Test(expected = InvalidCommandException.class)
    public void invalid_command_no_args() throws InvalidCommandException {
        parser.parseCommand("Driver");
    }

    @Test(expected = InvalidCommandException.class)
    public void invalid_command_unknown_command() throws InvalidCommandException {
        parser.parseCommand("Drivers Scott and Jim");
    }

    @Test
    public void parse_driver() throws InvalidCommandException {
        Driver driver = parser.parseDriver("Driver Alex");
        assertEquals("Should properly parse driver", new Driver("Alex"), driver);
    }

    @Test(expected = InvalidCommandException.class)
    public void parse_driver_invalid_arg_count() throws InvalidCommandException {
       parser.parseDriver("Driver Ilya Alex Dan");
    }

    @Test(expected = InvalidCommandException.class)
    public void parse_driver_invalid_command() throws InvalidCommandException {
        parser.parseDriver("Trip Dan 07:15 07:45 17.3");
    }

    @Test
    public void parse_trip() throws InvalidCommandException {
        Trip trip = parser.parseTrip("Trip Alex 12:01 13:16 42.0");
        assertEquals("Driver should parse correctly", new Driver("Alex"), trip.getDriver());
        assertEquals("Start time should parse correctly", CommandLineParser.parseTime("12:01"), trip.getStartTime());
        assertEquals("Stop time should parse correctly", CommandLineParser.parseTime("13:16"), trip.getStopTime());
        assertEquals("Miles Driven should parse correctly", new BigDecimal("42.0"), trip.getMilesDriven());
    }

    @Test(expected = InvalidCommandException.class)
    public void parse_trip_invalid_arg_count() throws InvalidCommandException {
        parser.parseTrip("Trip Dan 06:12 06:32 12.9 Driver Alex");
    }

    @Test(expected = InvalidCommandException.class)
    public void parse_trip_invalid_command() throws InvalidCommandException {
        parser.parseTrip("Trips Alex 12:01 13:16 42.0");
    }

    @Test(expected = InvalidCommandException.class)
    public void parse_trip_invalid_start_time() throws InvalidCommandException {
        parser.parseTrip("Trip Alex 12:01: 13:16 42.0");
    }

    @Test(expected = InvalidCommandException.class)
    public void parse_trip_invalid_stop_time() throws InvalidCommandException {
        parser.parseTrip("Trip Alex 12:01 13:16TT 42.0");
    }

    @Test(expected = InvalidCommandException.class)
    public void parse_trip_invalid_miles_driven() throws InvalidCommandException {
        parser.parseTrip("Trip Alex 12:01 13:16 AAA");
    }

    @Test(expected = InvalidCommandException.class)
    public void parse_trip_start_time_must_be_before_stop_time() throws InvalidCommandException {
        parser.parseTrip("Trip Alex 14:01 13:16 25.0");
    }

}
