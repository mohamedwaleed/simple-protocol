package my.simple.protocol.parser.exceptions;

public class UnknownCommandException extends Exception {
    @Override
    public String getMessage() {
        return "Unknown command";
    }
}
