package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameManager {
    private static final GameManager ourInstance = new GameManager();

    public int currentPlayer = 0;
    public GameData gameData;
    private Json json = new Json();//Object that will be saving the game data
    private FileHandle fileHandle = Gdx.files.local("bin/GameData1.json");


    public boolean gameStartedFromMainMenu, isPaused = true;
    public int lifeScore, coinScore, score;

    private Music music;

    public static GameManager getInstance() {
        return ourInstance;
    }


    private GameManager() {
    }

    public void initializeGameData() {
        if (!fileHandle.exists()) {//If The file at the given path of filehandle does not exist intitlize the data
            gameData = new GameData();

            gameData.setHighscore(0);
            gameData.setCoinHighscore(0);
            gameData.setCoinScore(0);

            gameData.setFemaleIsUnlocked(false);
            gameData.setPlayerIsUnlocked(false);
            gameData.setSoldierIsUnlocked(false);
            gameData.setZombieIsUnlocked(false);

            saveData();
        } else {
            loadData();
        }
    }

    public void saveData() {
        if (gameData != null) {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)), false);//json prettyPrint prints everything in Gamedata as a String in to the GameData.json file

        }
    }

    public void loadData() {
        gameData = json.fromJson(GameData.class,Base64Coder.decodeString(fileHandle.readString()));//Specifies which class we want to read from
    }


    public void checkForNewHighscores() {
        int oldHighscore = gameData.getHighscore();
        int oldCoinScore = gameData.getCoinScore();

        if (oldHighscore < score) {//If old high score is less than the current score
            gameData.setHighscore(score);
        }

        if (oldCoinScore < coinScore) {
            gameData.setCoinScore(coinScore);
        }
        saveData();
    }

    public boolean purchased() {
        if (currentPlayer == 0) {
            return gameData.isFemaleIsUnlocked();
        } else if (currentPlayer == 1) {
            return gameData.isPlayerIsUnlocked();
        } else if (currentPlayer == 2) {
            return gameData.isSoldierIsUnlocked();
        } else {
            return gameData.isZombieIsUnlocked();
        }

    }

    public String returnPlayerName() {
        if (currentPlayer == 0 && gameData.isFemaleIsUnlocked()) {
            return "Female";
        } else if (currentPlayer == 1 && gameData.isPlayerIsUnlocked()) {
            return "Male";
        } else if (currentPlayer == 2 && gameData.isSoldierIsUnlocked()) {
            return "Soldier";
        } else if (currentPlayer == 3 && gameData.isZombieIsUnlocked()) {
            return "Zombie";
        }else{
            return "Player";
        }
    }

    public void unlocker(int i){
        switch(i){
            case 0:
                gameData.setFemaleIsUnlocked(true);
                saveData();
                break;
            case 1:
                gameData.setPlayerIsUnlocked(true);
                saveData();
                break;
            case 2:
                gameData.setSoldierIsUnlocked(true);
                saveData();
                break;
            case 3:
                gameData.setZombieIsUnlocked(true);
                saveData();
                break;
        }
    }

    public void playMusic(){
        if(music == null){
            music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/MM2.mp3"));
        }

        if(!music.isPlaying()){
            music.play();
        }
    }

    public void stopMusic(){
        if(music.isPlaying()){
            music.stop();
            music.dispose();
        }
    }

    public void coinReducer(int i){
        switch (i){
            case 0:
                gameData.setCoinScore(coinScore - 10);
                saveData();
                break;
            case 1:
                gameData.setCoinScore(coinScore - 20);
                saveData();
                break;
            case 2:
                gameData.setCoinScore(coinScore - 30);
                saveData();
                break;
            case 3:
                gameData.setCoinScore(coinScore - 100);
                saveData();
                break;
        }
    }
}


