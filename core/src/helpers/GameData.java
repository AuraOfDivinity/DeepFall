package helpers;

public class GameData {
    private int highscore;
    private int coinHighscore;

    private int coinScore;

    private boolean playerIsUnlocked, femaleIsUnlocked, soldierIsUnlocked, zombieIsUnlocked;

    private boolean musicOn;

    public boolean isPlayerIsUnlocked() {
        return playerIsUnlocked;
    }

    public void setPlayerIsUnlocked(boolean playerIsUnlocked) {
        this.playerIsUnlocked = playerIsUnlocked;
    }

    public boolean isFemaleIsUnlocked() {
        return femaleIsUnlocked;
    }

    public void setFemaleIsUnlocked(boolean femaleIsUnlocked) {
        this.femaleIsUnlocked = femaleIsUnlocked;
    }

    public boolean isSoldierIsUnlocked() {
        return soldierIsUnlocked;
    }

    public void setSoldierIsUnlocked(boolean soldierIsUnlocked) {
        this.soldierIsUnlocked = soldierIsUnlocked;
    }

    public boolean isZombieIsUnlocked() {
        return zombieIsUnlocked;
    }

    public void setZombieIsUnlocked(boolean zombieIsUnlocked) {
        this.zombieIsUnlocked = zombieIsUnlocked;
    }




    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public int getCoinHighscore() {
        return coinHighscore;
    }

    public void setCoinHighscore(int coinHighscore) {
        this.coinHighscore = coinHighscore;
    }

    public int getCoinScore() {
        return coinScore;
    }

    public void setCoinScore(int coinScore) {
        this.coinScore = coinScore;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }
}
