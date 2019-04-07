package pads;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import Collectables.Collectable;
import helpers.GameManager;
import players.Player;


import helpers.GameInfo;

public class PadController {
    private World world;
    private Array<Pad> pads = new Array<Pad>(); //Arraylist because resizable
    private Array<Collectable> collectables = new Array<Collectable>();

    private final float DISTANCE_BETWEEN_PADS = 250f;

    private float minX, maxX;

    private Random random = new Random();

    private float cameraY;
    private float lastCloundPositionY;

    public PadController(World world) {
        this.world = world;
        minX = GameInfo.WIDTH / 2f - 140;
        maxX = GameInfo.WIDTH / 2f + 140;

        createPads();
        positionPads(true);//Arranging the pads for the first time
    }

    void createPads() {

        switch (GameInfo.gameLevel) {
            case 1:
                for (int i = 0; i < 2; i++) {
                    pads.add(new Pad(world, "pad 2"));
                }


                for (int i = 0; i < 6; i++) {
                    pads.add(new Pad(world, "pad 1"));
                }

                pads.shuffle();
                break;

            case 2:


                for (int i = 0; i < 2; i++) {
                    pads.add(new Pad(world, "pad 4"));
                }


                for (int i = 0; i < 6; i++) {
                    pads.add(new Pad(world, "pad 3"));
                }

                pads.shuffle();
                break;

            case 3:


                for (int i = 0; i < 2; i++) {
                    pads.add(new Pad(world, "pad 6"));
                }


                for (int i = 0; i < 6; i++) {
                    pads.add(new Pad(world, "pad 5"));
                }

                pads.shuffle();
                break;
            case 4:


                for (int i = 0; i < 2; i++) {
                    pads.add(new Pad(world, "pad 8"));
                }


                for (int i = 0; i < 6; i++) {
                    pads.add(new Pad(world, "pad 7"));
                }

                pads.shuffle();
                break;
        }
    }

    public void positionPads(boolean firstTimeArranging) {


        switch (GameInfo.gameLevel) {
            case 1:
                while (pads.get(0).getPadName() == "pad 2" || pads.get(1).getPadName() == "pad 2") {
                    pads.shuffle();
                }

                float positionY;

                if (firstTimeArranging) {
                    positionY = GameInfo.HEIGHT / 2f;
                } else {
                    positionY = lastCloundPositionY;
                }

                int controlX = 0;

                for (Pad c : pads) {
                    if (c.getX() == 0 && c.getY() == 0) {
                        float tempX = 0;

                        if (controlX == 0) {
                            tempX = randomBetweenNumbers(maxX - 60, maxX);
                            controlX = 1;
                        } else if (controlX == 1) {
                            tempX = randomBetweenNumbers(minX, minX + 60);
                            controlX = 0;
                        }

                        c.setSpritePosition(tempX, positionY);
                        positionY -= DISTANCE_BETWEEN_PADS; // positioning the pads 250px away from each other
                        lastCloundPositionY = positionY;

                        if(!firstTimeArranging && c.getPadName() != "pad 2"){
                            int rand = random.nextInt(10);//Resturn a random variable between 0-10
                            if(rand > 2){// Spawning collectables randomly
                                int randomCollectable = random.nextInt(2);

                                if(randomCollectable == 0){

                                    Collectable collectable = new Collectable(world, "Coin2");
                                    collectable.setCollectiblePosition(c.getX(), c.getY());

                                    collectables.add(collectable);
                                }
                                else{

                                    if(GameManager.getInstance().lifeScore < 2) {
                                        Collectable collectable = new Collectable(world, "Life2");
                                        collectable.setCollectiblePosition(c.getX(), c.getY());

                                        collectables.add(collectable);
                                    }
                                    else{//spawning a coin instead of a life when lives are = 2
                                        Collectable collectable = new Collectable(world, "Coin2");
                                        collectable.setCollectiblePosition(c.getX(), c.getY());

                                        collectables.add(collectable);
                                    }
                                }

                            }
                        }

                    }
                    //New textures are defaultly placed at the coordinates 0,0



                }



                break;

            case 2:


                while (pads.get(0).getPadName() == "pad 4" || pads.get(1).getPadName() == "pad 3") {
                    pads.shuffle();
                }

                if (firstTimeArranging) {
                    positionY = GameInfo.HEIGHT / 2f;
                } else {
                    positionY = lastCloundPositionY;
                }

                controlX = 0;

                for (Pad c : pads) {
                    if (c.getX() == 0 && c.getY() == 0) {
                        float tempX = 0;

                        if (controlX == 0) {
                            tempX = randomBetweenNumbers(maxX - 60, maxX);
                            controlX = 1;
                        } else if (controlX == 1) {
                            tempX = randomBetweenNumbers(minX, minX + 60);
                            controlX = 0;
                        }

                        c.setSpritePosition(tempX, positionY);
                        positionY -= DISTANCE_BETWEEN_PADS; // positioning the pads 250px away from each other
                        lastCloundPositionY = positionY;

                        if(!firstTimeArranging && c.getPadName() != "pad 4"){
                            int rand = random.nextInt(10);//Resturn a random variable between 0-10
                            if(rand > 2){// Spawning collectables randomly
                                int randomCollectable = random.nextInt(2);

                                if(randomCollectable == 0){

                                    Collectable collectable = new Collectable(world, "Coin2");
                                    collectable.setCollectiblePosition(c.getX(), c.getY());

                                    collectables.add(collectable);
                                }
                                else{
                                    if(GameManager.getInstance().lifeScore < 2) {
                                        Collectable collectable = new Collectable(world, "Life2");
                                        collectable.setCollectiblePosition(c.getX(), c.getY());

                                        collectables.add(collectable);
                                    }
                                    else{
                                        Collectable collectable = new Collectable(world, "Coin2");
                                        collectable.setCollectiblePosition(c.getX(), c.getY());

                                        collectables.add(collectable);
                                    }
                                }

                            }
                        }

                    }



                }
                break;

            case 3:
                while (pads.get(0).getPadName() == "pad 6" || pads.get(1).getPadName() == "pad 5") {
                    pads.shuffle();
                }


                if (firstTimeArranging) {
                    positionY = GameInfo.HEIGHT / 2f;
                } else {
                    positionY = lastCloundPositionY;
                }

                controlX = 0;

                for (Pad c : pads) {
                    if (c.getX() == 0 && c.getY() == 0) {
                        float tempX = 0;

                        if (controlX == 0) {
                            tempX = randomBetweenNumbers(maxX - 60, maxX);
                            controlX = 1;
                        } else if (controlX == 1) {
                            tempX = randomBetweenNumbers(minX, minX + 60);
                            controlX = 0;
                        }

                        c.setSpritePosition(tempX, positionY);
                        positionY -= DISTANCE_BETWEEN_PADS; // positioning the pads 250px away from each other
                        lastCloundPositionY = positionY;

                        if(!firstTimeArranging && c.getPadName() != "pad 6"){
                            int rand = random.nextInt(10);//Resturn a random variable between 0-10
                            if(rand > 2){// Spawning collectables randomly
                                int randomCollectable = random.nextInt(2);

                                if(randomCollectable == 0){

                                    Collectable collectable = new Collectable(world, "Coin2");
                                    collectable.setCollectiblePosition(c.getX(), c.getY());

                                    collectables.add(collectable);
                                }
                                else{
                                    if(GameManager.getInstance().lifeScore < 2) {//Allowing the player to have a maximum of two lives
                                        Collectable collectable = new Collectable(world, "Life2");
                                        collectable.setCollectiblePosition(c.getX(), c.getY());

                                        collectables.add(collectable);
                                    }
                                    else{
                                        Collectable collectable = new Collectable(world, "Coin2");
                                        collectable.setCollectiblePosition(c.getX(), c.getY());

                                        collectables.add(collectable);
                                    }
                                }

                            }
                        }

                    }



                }


                break;
            case 4:


                while (pads.get(0).getPadName() == "pad 8" || pads.get(1).getPadName() == "pad 7") {
                    pads.shuffle();
                }

                if (firstTimeArranging) {
                    positionY = GameInfo.HEIGHT / 2f;
                } else {
                    positionY = lastCloundPositionY;
                }

                controlX = 0;

                for (Pad c : pads) {
                    if (c.getX() == 0 && c.getY() == 0) {
                        float tempX = 0;

                        if (controlX == 0) {
                            tempX = randomBetweenNumbers(maxX - 60, maxX);
                            controlX = 1;
                        } else if (controlX == 1) {
                            tempX = randomBetweenNumbers(minX, minX + 60);
                            controlX = 0;
                        }

                        c.setSpritePosition(tempX, positionY);
                        positionY -= DISTANCE_BETWEEN_PADS; // positioning the pads 250px away from each other
                        lastCloundPositionY = positionY;

                        if(!firstTimeArranging && c.getPadName() != "pad 8"){
                            int rand = random.nextInt(10);//Resturn a random variable between 0-10
                            if(rand > 2){// Spawning collectables randomly
                                int randomCollectable = random.nextInt(2);

                                if(randomCollectable == 0){

                                    Collectable collectable = new Collectable(world, "Coin2");
                                    collectable.setCollectiblePosition(c.getX(), c.getY());

                                    collectables.add(collectable);
                                }
                                else{
                                    if(GameManager.getInstance().lifeScore < 2) {
                                        Collectable collectable = new Collectable(world, "Life2");
                                        collectable.setCollectiblePosition(c.getX(), c.getY());

                                        collectables.add(collectable);
                                    }
                                    else{
                                        Collectable collectable = new Collectable(world, "Coin2");
                                        collectable.setCollectiblePosition(c.getX(), c.getY());

                                        collectables.add(collectable);
                                    }
                                }

                            }
                        }

                    }



                }
                break;
        }
    }



    public void drawPads(SpriteBatch batch) {
        for (Pad c : pads) {
            batch.draw(c, c.getX() - c.getWidth() / 2f, c.getY() - c.getHeight() / 2f);
        }
    }

    public void drawCollectables(SpriteBatch batch){
        for(Collectable c: collectables){
            c.updateCollecteable();
            batch.draw(c, c.getX(), c.getY());
        }
    }

    public void removeCollectables(){
        for(int i = 0; i < collectables.size; i++){
            if(collectables.get(i).getFixture().getUserData() == "Remove"){
                collectables.get(i).changeFIlter();
                collectables.get(i).getTexture().dispose();
                collectables.removeIndex(i);
            }
        }
    }


    private float randomBetweenNumbers(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public void createAndArrangeClouds() {
        for (int i = 0; i < pads.size; i++) {
            if ((pads.get(i).getY() - GameInfo.HEIGHT / 2 - 15) > cameraY) {  //Checking whether the cloud is out of bounds
                pads.get(i).getTexture().dispose();
                pads.removeIndex(i);
            }
        }

        if (pads.size == 4) {
            createPads();
            positionPads(false); //Not positioning the pads for the first time
        }
    }

    public void removeOffScreenCollctables(){
        for(int i = 0; i < collectables.size; i++){
            if((collectables.get(i).getY() - GameInfo.HEIGHT / 2f -15) > cameraY){//Collectable us out of screen
                collectables.get(i).getTexture().dispose();
                collectables.removeIndex(i);
                System.out.println("The collectable has been removed");
            }
        }
    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }

    public Player positionThePlayer(Player player) {
        player = new Player(world, pads.get(0).getX(), pads.get(0).getY() + 100);
        return player;
    }

}
