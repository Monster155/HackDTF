package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ru.itlab.hackdtf.CreateFixture;

public class Player extends CharacterParent {
    public Fixture body;
    public Texture texture;
    final int speed = 30000;
    Joystick joystick;
    public final int health = 2;
    Array<Enemy> enemyArray;

    public Player(Joystick joystick, World world, EnemiesArray enemies) {
        this.joystick = joystick;
        enemyArray = enemies.enemyArray;
        texture = new Texture(Gdx.files.internal("player.png"));
        body = CreateFixture.createCircle(world, new Vector2(320, 180), 25, false, "player", (short) 1);
        body.getBody().setTransform(new Vector2(320, 180), 0);
    }

    public void shoot() {
        Gdx.app.log("Player", "Bulya pidr");

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.getBody().setLinearVelocity(joystick.cos * speed * delta, joystick.sin * speed * delta);
        //body.getBody().getTransform().setRotation((float) Math.atan2(x, y));
        double minDistance = 1000;
        for (Enemy e : enemyArray) {
            float x = body.getBody().getPosition().x;
            float y = body.getBody().getPosition().y;
            if (Math.pow(x, 2) + Math.pow(y, 2) <= minDistance) {
                minDistance = Math.pow(x - e.getX(), 2) + Math.pow(y-e.getY(), 2);
            }
        }
        for (Enemy e : enemyArray) {
            float x = body.getBody().getPosition().x;
            float y = body.getBody().getPosition().y;
            if (Math.pow(x - e.getX(), 2) + Math.pow(y-e.getY(), 2) >= minDistance - 0.000001 && Math.pow(x, 2) + Math.pow(y, 2) <= minDistance + 0.000001) {
                body.getBody().getTransform().setRotation(e.getRotation() + (float)Math.PI);
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
