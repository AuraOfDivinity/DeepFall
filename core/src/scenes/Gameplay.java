package scenes;

//Gameplay contains all the gameplay of the game

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

import helpers.GameManager;
import huds.UIHud;
import pads.PadController;
import helpers.GameInfo;
import players.Player;

public class Gameplay implements Screen, ContactListener{

    private GameMain game;

    private OrthographicCamera mainCamera;
    private Viewport gameViewport; //What we are seeing on the screen is the game viewport

    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer; //Used to draw bodies on the screen

    private World world;

    private boolean touchedForTheFirstTime;

    private Sprite[] bgs; //An array to store the backgrounds
    private float lastYPosition;

    private PadController padController;

    private UIHud hud;

    private Player player;
    private float lastPlayerY;


    public Gameplay(GameMain game) {
        this.game = game;
        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);
        createBackgrounds();

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);
        box2DCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        debugRenderer = new Box2DDebugRenderer();

        hud = new UIHud(game);

        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(this);//Inform the listener that the contact listener is this class

        padController = new PadController(world);

        player = padController.positionThePlayer(player);
        lastPlayerY = player.getY();


    }

    void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {//Check whether the left arrow key is pressed

            player.movePlayer(-2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            player.movePlayer(2);
        } else {
            player.setWalking(false);
        }
    }

    void handleInputAndroid(){
        if(Gdx.input.isTouched()){
            if(Gdx.input.getX() > GameInfo.WIDTH / 2){
                player.movePlayer(2);
            }else{
                player.movePlayer(-2);
            }
        }else{
            player.setWalking(false);
        }
    }

    void checkForFirstTouch(){//Checking for the fist touch from the user
        if(!touchedForTheFirstTime){
            if(Gdx.input.isTouched()){
                touchedForTheFirstTime = true;
                GameManager.getInstance().isPaused = false;
            }
        }
    }

    void update(float dt) {

        checkForFirstTouch();

        if(!GameManager.getInstance().isPaused){//Checking whether the game is paused or not
            handleInput(dt);
            handleInputAndroid();
            moveCamera();
            checkBackgroundsOutOfBounds();
            padController.setCameraY(mainCamera.position.y);
            padController.createAndArrangeClouds();
            padController.removeOffScreenCollctables();
            checkPlayersBounds();
            countScore();
        }
    }



    void moveCamera() {
        switch (GameInfo.gameLevel) {
            case 1:
                mainCamera.position.y -= 1.5;
                break;
            case 2:
                mainCamera.position.y -= 2;
                break;
            case 3:
                mainCamera.position.y -= 3;
                break;
            case 4:
                mainCamera.position.y -= 4.5;
                break;

      }

    }

    void createBackgrounds() {
        bgs = new Sprite[3];
        switch (GameInfo.gameLevel) {
            case 1:
                for (int i = 0; i < bgs.length; i++) {

                    bgs[i] = new Sprite(new Texture("Backgrounds/BG Edited Jungle.png"));
                    bgs[i].setPosition(0, -(i * bgs[i].getHeight()));
                    lastYPosition = Math.abs(bgs[i].getY()); //Taking the absolute value bacause y axis is negative
                }
                break;

            case 2:
                for (int i = 0; i < bgs.length; i++) {

                    bgs[i] = new Sprite(new Texture("Backgrounds/BG Edited City.png"));
                    bgs[i].setPosition(0, -(i * bgs[i].getHeight()));
                    lastYPosition = Math.abs(bgs[i].getY()); //Taking the absolute value bacause y axis is negative
                }
                break;

            case 3:
                for (int i = 0; i < bgs.length; i++) {

                    bgs[i] = new Sprite(new Texture("Backgrounds/BG Edited Beach.png"));
                    bgs[i].setPosition(0, -(i * bgs[i].getHeight()));
                    lastYPosition = Math.abs(bgs[i].getY()); //Taking the absolute value bacause y axis is negative
                }
                break;
            case 4:
                for (int i = 0; i < bgs.length; i++) {

                    bgs[i] = new Sprite(new Texture("Backgrounds/BG Edited Challenger.png"));
                    bgs[i].setPosition(0, -(i * bgs[i].getHeight()));
                    lastYPosition = Math.abs(bgs[i].getY()); //Taking the absolute value bacause y axis is negative
                }
                break;
        }
    }

    void drawBackgrounds() {
        for (int i = 0; i < bgs.length; i++) {
            game.getBatch().draw(bgs[i], bgs[i].getX(), bgs[i].getY());
        }
    }

    void checkBackgroundsOutOfBounds() { //Creates an infinite amount of backgrounds by adding more backgrounds when the camera is moving down
        for (int i = 0; i < bgs.length; i++) {
            if ((bgs[i].getY() - bgs[i].getHeight() / 2f - 5) > mainCamera.position.y) {
                float newPosition = bgs[i].getHeight() + lastYPosition;
                bgs[i].setPosition(0, -newPosition);
                lastYPosition = Math.abs(newPosition);
            }
        }
    }

    void checkPlayersBounds(){
        if(player.getY() - GameInfo.HEIGHT / 2f - player.getHeight() / 2f > mainCamera.position.y){//Player out of bound supward
            if(!player.isDead()){
                playerDied();
            }
        }

        if(player.getY() + GameInfo.HEIGHT / 2f + player.getHeight() / 2f < mainCamera.position.y){//Player out of bounds downwards
            if(!player.isDead()){
                playerDied();
            }
        }

        if((player.getX() - 60 > GameInfo.WIDTH) || player.getX() +60 < 0){
            if(!player.isDead()){
                playerDied();
            }
        }

    }

    void countScore(){
        if (lastPlayerY > player.getY()){
            hud.incrementScore(1);
            lastPlayerY = player.getY();
        }
    }

    void playerDied(){
        GameManager.getInstance().isPaused = true;
        hud.decrementLife();
        player.setDead(true);
        player.setPosition(1000, 1000);

        if(GameManager.getInstance().lifeScore < 0 ){//Player has no lives left

            GameManager.getInstance().checkForNewHighscores();
            hud.createGameOverPanel();

            RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MainMenu(game));
                }
            });

            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(3f));
            sa.addAction(Actions.fadeOut(1f));
            sa.addAction(run);

            hud.getStage().addAction(sa);
        }else{
            RunnableAction run = new RunnableAction();//Allows to call custom code.
            run.setRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new Gameplay(game));
                }
            });

            SequenceAction sa = new SequenceAction();//Enables the user to control the order the actions take place in
            sa.addAction(Actions.delay(1f));
            sa.addAction(Actions.fadeOut(1f));
            sa.addAction(run);

            hud.getStage().addAction(sa);
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        drawBackgrounds();//Draw functions MUST be called with the begin and the end batch functions.
        padController.drawPads(game.getBatch());
        padController.drawCollectables(game.getBatch());
        player.drawPlayerIdle(game.getBatch());
        player.drawPlayerAnimation(game.getBatch());
        game.getBatch().end();

        //debugRenderer.render(world, box2DCamera.combined);



        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
        hud.getStage().act();

        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();

        player.updatePlayer();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        //gameViewport.update(GameInfo.WIDTH, GameInfo.HEIGHT);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        for (int i = 0; i < bgs.length; i++) {
            bgs[i].getTexture().dispose();
        }
        player.getTexture().dispose();
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture body1, body2;

        if(contact.getFixtureA().getUserData() == "Player"){
            body1 = contact.getFixtureA();//We want player to be body1
            body2 = contact.getFixtureB();
        }else{
            body1 = contact.getFixtureB();
            body2 = contact.getFixtureA();
        }

        if(body1.getUserData() == "Player" && body2.getUserData() == "Coin2"){
            hud.incrementCoins();
            body2.setUserData("Remove");
            padController.removeCollectables();
        }
        if(body1.getUserData() == "Player" && body2.getUserData() == "pad 2"){
            if(!player.isDead()){
                playerDied();
            }
        }

        if(body1.getUserData() == "Player" && body2.getUserData() == "Life2"){
            hud.incrementLifes();
            body2.setUserData("Remove");
            padController.removeCollectables();
        }

        if(body1.getUserData() == "Player" && body2.getUserData() == "pad 4"){
            if(!player.isDead()){
                playerDied();
            }
        }

        if(body1.getUserData() == "Player" && body2.getUserData() == "pad 6"){
            if(!player.isDead()){
                playerDied();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
