package huds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

import helpers.GameInfo;
import scenes.MainMenu;
import scenes.Options;

public class OptionsButtons {
    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton easy, normal, hard, backBtn, easyClicked, normalClicked, hardClicked;
    private Image sign;

    public OptionsButtons(GameMain game) {
        this.game = game;

        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());
        Gdx.input.setInputProcessor(stage);

        createAndPositionButtons();
    }

    void createAndPositionButtons() {

        easy = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Options Buttons/Easy.png"))));
        hard = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Options Buttons/Hard.png"))));
        normal = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Options Buttons/Normal.png"))));
        backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Options Buttons/Home.png"))));

        easyClicked = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Options Buttons/EasyClicked.png"))));
        hardClicked = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Options Buttons/HardClicked.png"))));
        normalClicked = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Options Buttons/NormalClicked.png"))));

        easy.setPosition(GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2 + 80, Align.center);
        normal.setPosition(GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2 - 10, Align.center);
        hard.setPosition(GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2 - 100, Align.center);
        backBtn.setPosition(17, 17, Align.bottomLeft);

        easyClicked.setPosition(GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2 + 80, Align.center);
        normalClicked.setPosition(GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2 - 10, Align.center);
        hardClicked.setPosition(GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2 - 100, Align.center);

        addAllListeners();

        switch (GameInfo.gameLevel) {
            case 1:
                stage.addActor(easyClicked);
                stage.addActor(normal);
                stage.addActor(hard);
                stage.addActor(backBtn);
                break;

            case 2:
                stage.addActor(easy);
                stage.addActor(normalClicked);
                stage.addActor(hard);
                stage.addActor(backBtn);
                break;

            case 3:
                stage.addActor(easy);
                stage.addActor(normal);
                stage.addActor(hardClicked);
                stage.addActor(backBtn);
                break;
        }

    }

    void addAllListeners() {
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));

            }
        });
        easy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameInfo.gameLevel =1;
                game.setScreen(new Options(game));
            }
        });
        normal.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameInfo.gameLevel = 2;
                game.setScreen(new Options(game));
            }
        });
        hard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameInfo.gameLevel = 3;
                game.setScreen(new Options(game));
            }
        });
    }

    public Stage getStage() {
        return this.stage;
    }


}
