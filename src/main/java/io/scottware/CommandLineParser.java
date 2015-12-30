package io.scottware;

import io.scottware.exceptions.InvalidCommandException;
import io.scottware.interfaces.IParser;
import io.scottware.models.Command;
import io.scottware.models.Driver;
import io.scottware.models.Trip;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CommandLineParser implements IParser {

    public static final String DELIMITER = " ";
    public static final String DRIVER_COMMAND_STRING = "DRIVER";
    public static final String TRIP_COMMAND_STRING = "TRIP";
    public static final DateTimeFormatter STOP_START_DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm");


    @Override
    public Command parseCommand(String source) throws InvalidCommandException {
        if(source == null) {
            throw new InvalidCommandException("Command can not be null");
        }

        String[] split = source.split(DELIMITER);
        if(split.length < 2) {
            throw new InvalidCommandException("All commands must have at least one argument");
        }

        String command = split[0];
        if(command.equalsIgnoreCase(DRIVER_COMMAND_STRING)) {
            return Command.DRIVER;
        } else if (command.equalsIgnoreCase(TRIP_COMMAND_STRING)) {
            return Command.TRIP;
        } else {
            throw new InvalidCommandException("Unknown command: " + source);
        }
    }

    @Override
    public Driver parseDriver(String source) throws InvalidCommandException {
        if(parseCommand(source) != Command.DRIVER) {
            throw new InvalidCommandException("Must be a driver command to parse a driver");
        }

        String[] split = source.split(DELIMITER);
        if(split.length != 2) {
            throw new InvalidCommandException("Driver command must have only one argument");
        } else {
            return new Driver(split[1]);
        }
    }

    @Override
    public Trip parseTrip(String source) throws InvalidCommandException {
        if(parseCommand(source) != Command.TRIP) {
            throw new InvalidCommandException("Must be a trip command to parse a trip");
        }

        String[] split = source.split(DELIMITER);
        if(split.length != 5) {
            throw new InvalidCommandException("Trip command must have 4 arguments");
        } else {
            String driverName = split[1];
            String startTimeString = split[2];
            String stopTimeString = split[3];
            String milesDrivenString = split[4];

            Driver driver = new Driver(driverName);

            LocalTime startTime;
            try {
                startTime = parseTime(startTimeString);
            } catch (DateTimeParseException e) {
                throw new InvalidCommandException("Invalid start time format", e);
            }

            LocalTime stopTime;
            try {
                stopTime = parseTime(stopTimeString);
            } catch (DateTimeParseException e) {
                throw new InvalidCommandException("Invalid end time format", e);
            }

            if(!startTime.isBefore(stopTime)) {
                throw new InvalidCommandException("Start time must be before stop time.");
            }

            BigDecimal milesDriven;
            try {
                milesDriven = new BigDecimal(milesDrivenString);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException("Invalid miles driven format", e);
            }

            return new Trip(driver, startTime, stopTime, milesDriven);
        }
    }

    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, CommandLineParser.STOP_START_DATE_FORMAT);
    }
}
