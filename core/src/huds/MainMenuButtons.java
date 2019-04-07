package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;



import helpers.GameInfo;
import helpers.GameManager;
import scenes.Gameplay;
import scenes.Highscore;
import scenes.Options;
import scenes.Shop;

public class MainMenuButtons {

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton playBtn;
    private ImageButton highscoreBtn;
    private ImageButton optionBtn;
    private ImageButton quitBtn;
    private ImageButton musicBtn;
    private ImageButton shopBtn;
    private ImageButton infoBtn;
    private ImageButton challengerL;
    private ImageButton challengerU;

    private Label titleLabel;


    public MainMenuButtons(GameMain game) {
        this.game = game;
        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());
        createLabel();

        Gdx.input.setInputProcessor(stage);//Stage is the one that is going to register clicks we nake
        createAndPositionButtons();
        addAllListeners();
        checkMusic();
    }

    void createAndPositionButtons() {
        playBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Main Menu Buttons/Start Game.png"))));
        highscoreBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Main Menu Buttons/Highscore.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Main Menu Buttons/Quit.png"))));
        optionBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Main Menu Buttons/Options.png"))));
        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Main Menu Buttons/Music On.png"))));
        shopBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Main Menu Buttons/Shop.png"))));
        infoBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Main Menu Buttons/Info.png"))));
        challengerL = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Main Menu Buttons/Start Game CL.png"))));
        challengerU = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Main Menu Buttons/Start Game CU.png"))));

        playBtn.setPosition(GameInfo.WIDTH / 2f - 60, GameInfo.HEIGHT / 2f - 30, Align.center);
        challengerL.setPosition(GameInfo.WIDTH / 2f + 60, GameInfo.HEIGHT / 2f - 30, Align.center);
        challengerU.setPosition(GameInfo.WIDTH / 2f + 60, GameInfo.HEIGHT / 2f - 30, Align.center);
        highscoreBtn.setPosition(20, 10);

        optionBtn.setPosition(140, 10);
        shopBtn.setPosition(260, 10);
        infoBtn.setPosition(380, 10);
        musicBtn.setPosition(320,720);
        quitBtn.setPosition(400, 720);


        stage.addActor(playBtn);
        stage.addActor(highscoreBtn);
        stage.addActor(musicBtn);
        stage.addActor(optionBtn);
        stage.addActor(quitBtn);
        stage.addActor(shopBtn);
        stage.addActor(infoBtn);

        stage.addActor(titleLabel);

        if(GameManager.getInstance().gameData.getHighscore() > 2000){

            challengerL.remove();
            stage.addActor(challengerU);
        }else{

            stage.addActor(challengerL);
        }
    }

    void createLabel(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Mona Shark.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();//FreeTypeFontParameter is located within FreeTypeFontGenerator
        parameter.size = 70;
        BitmapFont font = generator.generateFont(parameter);

        titleLabel = new Label("Deep Fall",new Label.LabelStyle(font, Color.BLACK));

        titleLabel.setPosition(GameInfo.WIDTH/2f - 130, GameInfo.HEIGHT/2f + 50);
    }

    void addAllListeners(){
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //any code inside here will be exevuted when the playbtn is pressed
                GameManager.getInstance().gameStartedFromMainMenu =true;

                RunnableAction run = new RunnableAction();//Allows to call custom code.
                run.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new Gameplay(game));
                    }
                });

                SequenceAction sa = new SequenceAction();//Enables the user to control the order the actions take place in

                sa.addAction(Actions.fadeOut(1f));
                sa.addAction(run);

                stage.addAction(sa);


            }
        });

        highscoreBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //any code inside here will be exevuted when the playbtn is pressed
                game.setScreen(new Highscore(game));
            }
        });

        musicBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //any code inside here will be exevuted when the playbtn is pressed
            if(GameManager.getInstance().gameData.isMusicOn()){
                GameManager.getInstance().gameData.setMusicOn(false);
                GameManager.getInstance().stopMusic();
            }
            else{
                GameManager.getInstance().gameData.setMusicOn(true);
                GameManager.getInstance().playMusic();
            }
            }
        });



        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //any code inside here will be exevuted when the playbtn is pressed

            }
        });

        optionBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { //any code inside here will be exevuted when the playbtn is pressed
                game.setScreen(new Options(game));
            }
        });
        shopBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Shop(game));
            }
        });
        challengerU.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameInfo.gameLevel = 4;

                GameManager.getInstance().gameStartedFromMainMenu =true;

                RunnableAction run = new RunnableAction();//Allows to call custom code.
                run.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new Gameplay(game));
                    }
                });

                SequenceAction sa = new SequenceAction();//Enables the user to control the order the actions take place in

                sa.addAction(Actions.fadeOut(1f));
                sa.addAction(run);

                stage.addAction(sa);
            }
        });
    }

    void checkMusic(){
        if(GameManager.getInstance().gameData.isMusicOn()) {
            GameManager.getInstance().playMusic();
        }
    }

    public Stage getStage() {
        return this.stage;
    }

}
