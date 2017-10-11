package api.components;

public class Match {

    private String firstPlayerName;
    private int firstPlayerScore;
    private int firstPlayerMines;
    private long firstPlayerTime;
    private int firstPlayerHits;
    private int firstPlayerMisses;

    private String secondPlayerName;
    private int secondPlayerScore;
    private int secondPlayerMines;
    private long secondPlayerTime;
    private int secondPlayerHits;
    private int secondPlayerMisses;

    public Match(String firstPlayerName, int firstPlayerScore, int firstPlayerMines, long firstPlayerTime, int firstPlayerHits, int firstPlayerMisses,
                 String secondPlayerName, int secondPlayerScore, int secondPlayerMines, long secondPlayerTime, int secondPlayerHits, int secondPlayerMisses) {
        this.firstPlayerName = firstPlayerName;
        this.firstPlayerScore = firstPlayerScore;
        this.firstPlayerMines = firstPlayerMines;
        this.firstPlayerTime = firstPlayerTime;
        this.firstPlayerHits = firstPlayerHits;
        this.firstPlayerMisses = firstPlayerMisses;
        this.secondPlayerName = secondPlayerName;
        this.secondPlayerScore = secondPlayerScore;
        this.secondPlayerMines = secondPlayerMines;
        this.secondPlayerTime = secondPlayerTime;
        this.secondPlayerHits = secondPlayerHits;
        this.secondPlayerMisses = secondPlayerMisses;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public void setFirstPlayerName(String firstPlayerName) {
        this.firstPlayerName = firstPlayerName;
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public void setFirstPlayerScore(int firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public int getFirstPlayerMines() {
        return firstPlayerMines;
    }

    public void setFirstPlayerMines(int firstPlayerMines) {
        this.firstPlayerMines = firstPlayerMines;
    }

    public long getFirstPlayerTime() {
        return firstPlayerTime;
    }

    public void setFirstPlayerTime(long firstPlayerTime) {
        this.firstPlayerTime = firstPlayerTime;
    }

    public int getFirstPlayerHits() {
        return firstPlayerHits;
    }

    public void setFirstPlayerHits(int firstPlayerHits) {
        this.firstPlayerHits = firstPlayerHits;
    }

    public int getFirstPlayerMisses() {
        return firstPlayerMisses;
    }

    public void setFirstPlayerMisses(int firstPlayerMisses) {
        this.firstPlayerMisses = firstPlayerMisses;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public void setSecondPlayerName(String secondPlayerName) {
        this.secondPlayerName = secondPlayerName;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public void setSecondPlayerScore(int secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
    }

    public int getSecondPlayerMines() {
        return secondPlayerMines;
    }

    public void setSecondPlayerMines(int secondPlayerMines) {
        this.secondPlayerMines = secondPlayerMines;
    }

    public long getSecondPlayerTime() {
        return secondPlayerTime;
    }

    public void setSecondPlayerTime(long secondPlayerTime) {
        this.secondPlayerTime = secondPlayerTime;
    }

    public int getSecondPlayerHits() {
        return secondPlayerHits;
    }

    public void setSecondPlayerHits(int secondPlayerHits) {
        this.secondPlayerHits = secondPlayerHits;
    }

    public int getSecondPlayerMisses() {
        return secondPlayerMisses;
    }

    public void setSecondPlayerMisses(int secondPlayerMisses) {
        this.secondPlayerMisses = secondPlayerMisses;
    }
}
