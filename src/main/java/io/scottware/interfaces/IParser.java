package io.scottware.interfaces;

import io.scottware.models.Command;
import io.scottware.models.Driver;
import io.scottware.exceptions.InvalidCommandException;
import io.scottware.models.Trip;

public interface IParser {
    Command parseCommand(String source) throws InvalidCommandException;
    Driver parseDriver(String source) throws InvalidCommandException;
    Trip parseTrip(String source) throws InvalidCommandException;
}
