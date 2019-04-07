package pads;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

public class Pad extends Sprite {

    private World world;
    private Body body;
    private String padName;

    public Pad(World world, String padName) {
        super(new Texture("Pads/" + padName + ".png"));
        this.world = world;
        this.padName = padName;
    }

    void createBody() {

        switch (GameInfo.gameLevel) {
            case 1:
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;

                bodyDef.position.set((getX() - 45) / GameInfo.PPM, (getY() - 10) / GameInfo.PPM);

                body = world.createBody(bodyDef);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(((getWidth() / 2f - 13) / GameInfo.PPM), (getHeight() / 2f - 10) / GameInfo.PPM);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;

                Fixture fixture = body.createFixture(fixtureDef);
                fixture.setUserData(padName);
                shape.dispose();
                break;

            case 2:
                bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;

                bodyDef.position.set((getX() - 45) / GameInfo.PPM, (getY() -10) / GameInfo.PPM);

                body = world.createBody(bodyDef);

                shape = new PolygonShape();
                shape.setAsBox(((getWidth() / 2f - 150) / GameInfo.PPM )   , (getHeight() / 2f - 20) / GameInfo.PPM) ;

                fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;

                fixture = body.createFixture(fixtureDef);
                fixture.setUserData(padName);
                shape.dispose();
                break;

            case 3:
                bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;

                bodyDef.position.set((getX() - 45) / GameInfo.PPM, (getY() -10) / GameInfo.PPM);

                body = world.createBody(bodyDef);

                shape = new PolygonShape();
                shape.setAsBox(((getWidth() / 2f - 90) / GameInfo.PPM )   , (getHeight() / 2f - 20) / GameInfo.PPM) ;

                fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;

                fixture = body.createFixture(fixtureDef);
                fixture.setUserData(padName);
                shape.dispose();
                break;

            case 4:
                bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;

                bodyDef.position.set((getX() - 45) / GameInfo.PPM, (getY() -10) / GameInfo.PPM);

                body = world.createBody(bodyDef);

                shape = new PolygonShape();
                shape.setAsBox(((getWidth() / 2f - 150) / GameInfo.PPM )   , (getHeight() / 2f - 10) / GameInfo.PPM) ;

                fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;

                fixture = body.createFixture(fixtureDef);
                fixture.setUserData(padName);
                shape.dispose();
                break;
        }
    }

    public void setSpritePosition(float x, float y) {
        setPosition(x, y);
        createBody();
    }

    public String getPadName() {
        return this.padName;
    }
}
