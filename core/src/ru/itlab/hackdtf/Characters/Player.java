package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    public Fixture body;
    public Texture texture;
    float x = 0, y = 0;
    final int speed = 300;

    public Player(Joystick joystick) {
        x = joystick.cos;
        y = joystick.sin;
        texture = new Texture(" ");  //TODO add picture of Player and create fixture
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.getBody().setLinearVelocity(x * speed, y * speed);
        //body.getBody().getTransform().setRotation((float) Math.atan2(x, y));
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

    public void dispose() {
        texture.dispose();
    }
}
