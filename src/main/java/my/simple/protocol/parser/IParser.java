package my.simple.protocol.parser;

import my.simple.protocol.commands.Command;

public interface IParser {
    Command parse(String command);
}
