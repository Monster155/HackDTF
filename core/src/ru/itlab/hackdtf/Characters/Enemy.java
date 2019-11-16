package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import ru.itlab.hackdtf.CreateFixture;

public class Enemy extends CharacterParent {

    Texture texture;
    Fixture body;
    int speed = 50000;

    public Enemy(World world) {
        texture = new Texture(Gdx.files.internal("enemy.png"));
        body = CreateFixture.createCircle(world, new Vector2(320, 180), 25, false, "enemy", (short) 2);
        body.getBody().setTransform(new Vector2(200, 180), 0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.getBody().setLinearVelocity((float) Math.cos(Math.toDegrees(body.getBody().getAngle())) * speed * delta,
                (float) Math.sin(Math.toDegrees(body.getBody().getAngle())) * speed * delta);
        //body.getBody().getTransform().setRotation((float) Math.atan2(x, y));
        //TODO logic of enemies
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
