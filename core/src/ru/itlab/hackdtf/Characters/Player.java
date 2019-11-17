package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import ru.itlab.hackdtf.CreateFixture;
import ru.itlab.hackdtf.Screens.GameScreen;
import ru.itlab.hackdtf.Weapons.Gun;

public class Player extends Actor {

    public Fixture body;
    public static double minDistance;
    public Texture texture;
    boolean isSlowLast = false;
    int speed = 30000, id = 0;
    Joystick joystick;
    public final int health = 2;//TODO create guns
    public int bulletCount = 0;
    public Array<Enemy> enemies;
    public static Array<Gun> guns;
    public Gun playerGun;
    Stage stage;

    public Player(Stage stage, Joystick joystick, World world) {
        enemies = new Array<>();
        guns = new Array<>();
        this.joystick = joystick;
        texture = new Texture(Gdx.files.internal("player.png"));
        body = CreateFixture.createCircle(world, new Vector2(320, 180), 10, false, "player", (short) 1);
        body.getBody().setTransform(new Vector2(320, 180), 0);
        playerGun = new Gun(stage, world, 1, false, this);
        playerGun.bulletCount = 10;
        this.stage = stage;
        stage.addActor(playerGun);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isSlowLast != GameScreen.isSlow)
            useBraking(GameScreen.isSlow);

        body.getBody().setLinearVelocity(joystick.cos * speed * delta, joystick.sin * speed * delta);
        body.getBody().setAngularVelocity(0);
        body.getBody().setAngularDamping(0);
        playerGun.updatePos(body.getBody().getPosition(), (float) Math.toDegrees(body.getBody().getAngle()), body.getShape().getRadius());
        //body.getBody().getTransform().setRotation((float) Math.atan2(x, y));

        minDistance = 1000f;
        float xp = body.getBody().getPosition().x;
        float yp = body.getBody().getPosition().y;
        for (Enemy e : enemies) {
            float xe = e.body.getBody().getPosition().x;
            float ye = e.body.getBody().getPosition().y;

            double distance = Math.sqrt((xp - xe) * (xp - xe) + (yp - ye) * (yp - ye));
            if (distance < minDistance) {
                minDistance = distance;
                float angleRadian = (float) (Math.atan2((yp - ye) / minDistance, (xp - xe) / minDistance) + Math.PI);
                body.getBody().setTransform(body.getBody().getPosition(), angleRadian);
            }
        }

        minDistance = 1000f;
        id = 0;
        boolean founded = false;
        for (int i = 0; i < guns.size; i++) {
            if (!guns.get(i).isDropped) continue;

            double distance = Math.sqrt((xp - (guns.get(i).pos.x + guns.get(i).size.x / 2)) * (xp - (guns.get(i).pos.x + guns.get(i).size.x / 2))
                    + (yp - (guns.get(i).pos.y - guns.get(i).size.y / 2)) * (yp - (guns.get(i).pos.y - guns.get(i).size.y / 2)));

            if (distance < minDistance) {
                minDistance = distance;
                if (guns.get(i).isDropped && distance <= body.getShape().getRadius()) {
                    ActionButton.canTake = true;
                    id = i;
                    founded = true;
                } else if(!founded){
                    ActionButton.canTake = false;
                }
            }
        }
        //TODO check for find and take a new weapon
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture,
                body.getBody().getPosition().x - body.getShape().getRadius(),
                body.getBody().getPosition().y - body.getShape().getRadius(),
                body.getShape().getRadius() + 0, //center for rotation and scaling x
                body.getShape().getRadius() + 0, //center for rotation and scaling y
                body.getShape().getRadius() * 2,
                body.getShape().getRadius() * 2,
                1, 1, //scale from center
                (float) Math.toDegrees(body.getBody().getAngle()) + 0,
                0, 0, //coordinates in image file
                texture.getWidth() + 0, //size in image file
                texture.getHeight() + 0,
                false, false);
    }

    @Override
    public float getRotation() {
        return body.getBody().getAngle();
    }

    public void useBraking(boolean isSlow) {
        if (isSlow) speed /= GameScreen.braker;
        else speed *= GameScreen.braker;
        isSlowLast = isSlow;
    }

    public void pickUp() {
        playerGun.destroy();
        this.playerGun = guns.get(id);
        playerGun.isDropped = false;
        ActionButton.canTake = false;
    }
}
