package io.scottware;

import io.scottware.exceptions.InvalidCommandException;
import io.scottware.interfaces.IParser;
import io.scottware.interfaces.ITripTracker;
import io.scottware.models.Command;
import io.scottware.models.Driver;
import io.scottware.models.Trip;

import java.util.Scanner;

public class TripTrackerCommandLineClient {

    private IParser parser;
    private ITripTracker tripTracker;
    private int currentLine;

    public TripTrackerCommandLineClient(IParser parser, ITripTracker tripTracker) {
        this.parser = parser;
        this.tripTracker = tripTracker;
    }

    public void parseLine(String line) {
        currentLine++;
        try {
            Command command = parser.parseCommand(line);

            switch (command) {
                case DRIVER:
                    Driver driver = parser.parseDriver(line);
                    tripTracker.registerDriver(driver);
                    break;
                case TRIP:
                    Trip trip = parser.parseTrip(line);
                    tripTracker.logTrip(trip);
                    break;
            }
        } catch (InvalidCommandException e) {
            System.out.println("Invalid TripTrackerCommand on line " + currentLine + " of input. " +
                    "Message: " + e.getMessage());
        }
    }

    public void displayReport() {
        System.out.println( "=== Trip Tracker Report ===\n" +
                tripTracker.generateReport());
    }

    public static void main(String[] args) {
        IParser commandLineParser = new CommandLineParser();
        ITripTracker inMemoryTripTracker = new InMemoryTripTracker();
        TripTrackerCommandLineClient commandLineClient = new TripTrackerCommandLineClient(commandLineParser, inMemoryTripTracker);

        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {
            commandLineClient.parseLine(sc.nextLine());
        }
        commandLineClient.displayReport();
        System.exit(0);
    }


}
