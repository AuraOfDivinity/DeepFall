package huds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Image;





import helpers.GameInfo;
import helpers.GameManager;
import scenes.MainMenu;

public class UIHud{
    private GameMain game;
    private Stage stage;
    private Viewport gameViewPort;

    private Label coinLabel, lifeLabel, scoreLabel;
    private Image coinImg, lifeImg, scoreImg, pausePanel;

    private ImageButton pauseBtn, quitBtn, resumeBtn;


    public UIHud(GameMain game){
        this.game = game;

        gameViewPort = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewPort, game.getBatch()); //Requires sprite batch to draw all the elements

        Gdx.input.setInputProcessor(stage);

        if(GameManager.getInstance().gameStartedFromMainMenu){//Check whether this is the intial time starting the game

            GameManager.getInstance().gameStartedFromMainMenu =false;
            GameManager.getInstance().lifeScore = 2;
            GameManager.getInstance().coinScore = 0;
            GameManager.getInstance().score = 0;
        }

        createLabels();
        createImages();
        createBtnAndAddListener();

        Table coinTable = new Table();
        coinTable.top().left();
        coinTable.add(coinImg).padRight(10).padTop(10);//Moving the image 10px from the left

        coinTable.setFillParent(true);




        Table lifeTable = new Table();
        lifeTable.top().right();
        lifeTable.setFillParent(true);
        lifeTable.add(lifeImg).padLeft(10).padTop(10);

        Table scoreTable = new Table();
        scoreTable.left().bottom();
        scoreTable.setFillParent(true);
        scoreTable.add(scoreImg);
        //scoreTable.setDebug(true);


        stage.addActor(lifeTable);
        stage.addActor(coinTable);
        stage.addActor(scoreTable);
        stage.addActor(lifeLabel);
        stage.addActor(coinLabel);
        stage.addActor(scoreLabel);
        stage.addActor(pauseBtn);




    }

    void createLabels(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/RifficFree-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();//FreeTypeFontParameter is located within FreeTypeFontGenerator
        parameter.size = 20;
        BitmapFont font = generator.generateFont(parameter);

        coinLabel = new Label("x" + GameManager.getInstance().coinScore, new Label.LabelStyle(font, Color.WHITE));
        lifeLabel = new Label("x"+ GameManager.getInstance().lifeScore, new Label.LabelStyle(font, Color.WHITE));
        scoreLabel = new Label(""+ GameManager.getInstance().score, new Label.LabelStyle(font, Color.WHITE));

        coinLabel.setPosition(GameInfo.WIDTH/2 - 120, GameInfo.HEIGHT-75);
        lifeLabel.setPosition(GameInfo.WIDTH/2 + 50, GameInfo.HEIGHT - 75);
        scoreLabel.setPosition(GameInfo.WIDTH/2 -  70, 55);


    }

    void createImages(){
        coinImg = new Image(new Texture("Collectables/coin.png"));
        lifeImg = new Image(new Texture("Collectables/life.png"));
        scoreImg = new Image(new Texture("Collectables/score.png"));


    }

    void createBtnAndAddListener(){
        pauseBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Gameplay Buttons/Pause.png"))));

        pauseBtn.setPosition(480, -5, Align.bottomRight);
        pauseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {//Code for pausing the game

                if(!GameManager.getInstance().isPaused) {
                    GameManager.getInstance().isPaused = true;
                    createPausePanel();
                }
            }
        });

    }

    void createPausePanel(){
        pausePanel = new Image(new Texture("Buttons/Pause/Pause Panel.png"));
        resumeBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Pause/Resume.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Pause/Quit.png"))));

        pausePanel.setPosition(GameInfo.WIDTH /2f, GameInfo.HEIGHT /2f, Align.center);
        resumeBtn.setPosition(GameInfo.WIDTH /2f, GameInfo.HEIGHT /2f + 50, Align.center);
        quitBtn.setPosition(GameInfo.WIDTH /2f, GameInfo.HEIGHT /2f - 80, Align.center);

        resumeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                removePausePanel();
                GameManager.getInstance().isPaused = false;
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        stage.addActor(pausePanel);
        stage.addActor(resumeBtn);
        stage.addActor(quitBtn);


    }

    void removePausePanel(){
        pausePanel.remove();
        resumeBtn.remove();
        quitBtn.remove();
    }

    public void createGameOverPanel(){
        Image gameOverPanel = new Image(new Texture("Buttons/Pause/Show Score.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/RifficFree-Bold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;

        BitmapFont font = generator.generateFont(parameter);

        Label endScore  = new Label(String.valueOf(GameManager.getInstance().score), new Label.LabelStyle(font, Color.WHITE));
        Label endCoinScore  = new Label(String.valueOf(GameManager.getInstance().coinScore), new Label.LabelStyle(font, Color.WHITE));

        gameOverPanel.setPosition(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f, Align.center);

        endScore.setPosition(GameInfo.WIDTH /2f + 30, GameInfo.HEIGHT /2f +40, Align.center);
        endCoinScore.setPosition(GameInfo.WIDTH /2f +30, GameInfo.HEIGHT /2f - 55, Align.center);

        stage.addActor(gameOverPanel);
        stage.addActor(endScore);
        stage.addActor(endCoinScore);

    }

    public void incrementScore(int score){
        GameManager.getInstance().score += score;
        scoreLabel.setText(String.valueOf(GameManager.getInstance().score));
    }

    public void incrementCoins(){
        GameManager.getInstance().coinScore++;
        coinLabel.setText("x" + GameManager.getInstance().coinScore);
        incrementScore(200);
    }

    public void incrementLifes(){
        GameManager.getInstance().lifeScore++;
        lifeLabel.setText("x" + GameManager.getInstance().lifeScore);
        incrementScore(300);
    }

    public Stage getStage(){
        return this.stage;
    }

    public void decrementLife(){
        GameManager.getInstance().lifeScore--;
        if(GameManager.getInstance().lifeScore >= 0){
            lifeLabel.setText("x"+ GameManager.getInstance().lifeScore);
        }
    }


}
