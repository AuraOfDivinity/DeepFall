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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

import helpers.GameInfo;
import helpers.GameManager;
import scenes.MainMenu;
import scenes.Shop;

public class ShopButtons {

    private GameMain game;
    private Viewport gameViewport;
    private Stage stage;

    private ImageButton nextBtn;
    private ImageButton prevBtn;
    private ImageButton homeBtn;
    private ImageButton purchaseLockedBtn;
    private ImageButton purchaseBtn;
    private ImageButton musicBtn;

    private Image coinImg, femaleImg, maleImg , zombieImg, soldierImg;

    private Label coinAmt, femaleP, maleP, zombieP, soldierP;

    

    private Array<Image> playerImages = new Array<Image>();
    private float[] playerPrice = new float[4];

    public ShopButtons (GameMain game){
        this.game = game;

        
        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(gameViewport, game.getBatch());//Creating the stage
        Gdx.input.setInputProcessor(stage);

        createLabels();
        createImage();
        createAndPositionButtons();
        addAllListeners();

        stage.addActor(musicBtn);
        stage.addActor(homeBtn);
        stage.addActor(nextBtn);
        stage.addActor(prevBtn);
        stage.addActor(coinImg);
        stage.addActor(coinAmt);
        stage.addActor(playerImages.get(GameManager.getInstance().currentPlayer));

        if(GameManager.getInstance().purchased()){

        }else{
            if(GameManager.getInstance().gameData.getCoinScore() > playerPrice[GameManager.getInstance().currentPlayer]) {
                stage.addActor(purchaseBtn);
            }
            else{
                stage.addActor(purchaseLockedBtn);
            }
        }

        playerPrice[0] = 10;
        playerPrice[1] = 20;
        playerPrice[2] = 30;
        playerPrice[3] = 100;

    }

    void createImage(){
        coinImg = new Image(new Texture("Buttons/Shop Buttons/coin.png"));
        coinImg.setPosition(GameInfo.WIDTH /2f -140, GameInfo.HEIGHT /2 + 90);

        femaleImg = new Image(new Texture("Buttons/Shop Buttons/Female.png"));
        maleImg = new Image(new Texture("Buttons/Shop Buttons/Male.png"));
        soldierImg = new Image(new Texture("Buttons/Shop Buttons/Soldier.png"));
        zombieImg = new Image(new Texture("Buttons/Shop Buttons/Zombie.png"));

        femaleImg.setPosition(GameInfo.WIDTH / 2 - 40, GameInfo.HEIGHT / 2);
        maleImg.setPosition(GameInfo.WIDTH / 2 - 40, GameInfo.HEIGHT / 2);
        soldierImg.setPosition(GameInfo.WIDTH / 2 - 40, GameInfo.HEIGHT / 2);
        zombieImg.setPosition(GameInfo.WIDTH / 2 - 40, GameInfo.HEIGHT / 2);

        playerImages.add(femaleImg);
        playerImages.add(maleImg);
        playerImages.add(soldierImg);
        playerImages.add(zombieImg);
    }

    void createAndPositionButtons(){
        nextBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Shop Buttons/Next.png"))));
        prevBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Shop Buttons/Previous.png"))));
        homeBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Shop Buttons/Home.png"))));
        purchaseBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Shop Buttons/Purchase.png"))));
        purchaseLockedBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Shop Buttons/Purchase Locked.png"))));
        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Shop Buttons/Music On.png"))));


        homeBtn.setPosition(20, 10);
        musicBtn.setPosition(400, 720);
        nextBtn.setPosition(GameInfo.WIDTH / 2 + 50, GameInfo.HEIGHT /2 - 100);
        prevBtn.setPosition(GameInfo.WIDTH / 2 - 110, GameInfo.HEIGHT /2 - 100);
        purchaseBtn.setPosition(GameInfo.WIDTH / 2 - 40, GameInfo.HEIGHT /2 - 120);
        purchaseLockedBtn.setPosition(GameInfo.WIDTH / 2 - 40, GameInfo.HEIGHT /2 - 120);
    }

    void createLabels(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/RifficFree-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();//FreeTypeFontParameter is located within FreeTypeFontGenerator
        parameter.size = 20;
        BitmapFont font = generator.generateFont(parameter);

        coinAmt = new Label("" + GameManager.getInstance().gameData.getCoinScore(), new Label.LabelStyle(font, Color.WHITE));
        coinAmt.setPosition(GameInfo.WIDTH/2, GameInfo.HEIGHT /2 + 128);





    }


    void addAllListeners(){
        nextBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(GameManager.getInstance().currentPlayer == 3) {
                    if(GameManager.getInstance().purchased()){

                    }else{
                        if(GameManager.getInstance().gameData.getCoinScore() > playerPrice[GameManager.getInstance().currentPlayer]) {
                            stage.addActor(purchaseBtn);
                        }
                        else{
                            stage.addActor(purchaseLockedBtn);
                        }
                    }
                }
                else{
                    playerImages.get(GameManager.getInstance().currentPlayer).remove();
                    purchaseBtn.remove();
                    purchaseLockedBtn.remove();
                    GameManager.getInstance().currentPlayer++;

                    stage.addActor(playerImages.get(GameManager.getInstance().currentPlayer));
                    if(GameManager.getInstance().purchased()){

                    }else{
                        if(GameManager.getInstance().gameData.getCoinScore() > playerPrice[GameManager.getInstance().currentPlayer]) {
                            stage.addActor(purchaseBtn);
                        }
                        else{
                            stage.addActor(purchaseLockedBtn);
                        }
                    }
                }
            }
        });

        prevBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(GameManager.getInstance().currentPlayer == 0){
                    purchaseBtn.remove();
                    purchaseLockedBtn.remove();
                    if(GameManager.getInstance().purchased()){

                    }else{
                        if(GameManager.getInstance().gameData.getCoinScore() > playerPrice[GameManager.getInstance().currentPlayer]) {
                            stage.addActor(purchaseBtn);
                        }
                        else{
                            stage.addActor(purchaseLockedBtn);
                        }
                    }
                }
                else{
                    playerImages.get(GameManager.getInstance().currentPlayer).remove();
                    purchaseBtn.remove();
                    purchaseLockedBtn.remove();
                    GameManager.getInstance().currentPlayer--;

                    stage.addActor(playerImages.get(GameManager.getInstance().currentPlayer));
                    if(GameManager.getInstance().purchased()){

                    }else{
                        if(GameManager.getInstance().gameData.getCoinScore() > playerPrice[GameManager.getInstance().currentPlayer]) {
                            stage.addActor(purchaseBtn);
                        }
                        else{
                            stage.addActor(purchaseLockedBtn);
                        }
                    }
                }
            }
        });

        homeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));

            }
        });

        purchaseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.getInstance().unlocker(GameManager.getInstance().currentPlayer);
                GameManager.getInstance().coinReducer(GameManager.getInstance().currentPlayer);
            }
        });

        musicBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }


    public Stage getStage() {
        return stage;
    }
}
