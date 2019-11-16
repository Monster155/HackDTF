package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import ru.itlab.hackdtf.CreateFixture;
import ru.itlab.hackdtf.Weapons.Gun;

public class Player extends Actor {
    public Fixture body;
    public Texture texture;
    final int speed = 30000;
    Joystick joystick;
    public final int health = 2;//TODO create guns
    public int bulletCount = 0;
    public Array<Enemy> enemies;
    Gun gun;


    public Player(Stage stage, Joystick joystick, World world) {
        enemies = new Array<>();
        this.joystick = joystick;
        texture = new Texture(Gdx.files.internal("player.png"));
        body = CreateFixture.createCircle(world, new Vector2(320, 180), 25, false, "player", (short) 1);
        body.getBody().setTransform(new Vector2(320, 180), 0);
        gun = new Gun(stage, world, 1);
        stage.addActor(gun);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.getBody().setLinearVelocity(joystick.cos * speed * delta, joystick.sin * speed * delta);
        gun.updatePos(body.getBody().getPosition(), (float) Math.toDegrees(body.getBody().getAngle()), body.getShape().getRadius());
        //body.getBody().getTransform().setRotation((float) Math.atan2(x, y));

        double minDistance = 1000f;
        for (Enemy e : enemies) {
            float xp = body.getBody().getPosition().x;
            float yp = body.getBody().getPosition().y;
            float xe = e.body.getBody().getPosition().x;
            float ye = e.body.getBody().getPosition().y;

            double distance = Math.sqrt((xp - xe) * (xp - xe) + (yp - ye) * (yp - ye));
            if (distance < minDistance) {
                minDistance = distance;
                float angleRadian = (float) (Math.atan2((yp - ye) / minDistance, (xp - xe) / minDistance) + Math.PI);
                body.getBody().setTransform(body.getBody().getPosition(), angleRadian);
            }
        }
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
}
