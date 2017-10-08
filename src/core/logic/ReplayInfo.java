package core.logic;

public class ReplayInfo {

    private final String description;
    private char[][] personalBoard;
    private char[][] traceBoard;

    ReplayInfo(char[][] personal, char[][] trace, String description) {
        this.description = description;
        personalBoard = personal;
        traceBoard = trace;
    }

    public char[][] getPersonalBoard() {
        return personalBoard;
    }

    @Override
    public String toString() {
        return description;
    }

    public char[][] getTraceBoard() {
        return traceBoard;
    }
}
