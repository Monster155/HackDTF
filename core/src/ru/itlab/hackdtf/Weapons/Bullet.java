package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.itlab.hackdtf.CreateFixture;

public class Bullet extends Actor {

    Texture texture;
    Stage stage;
    Fixture body;
    int speed = 999999;

    public Bullet(float angleInRad, Vector2 pos, World world, Stage stage) {
        this.stage = stage;
        body = CreateFixture.createCircle(world, pos, 25, false, "pBullet", (short) 3);
        body.getBody().setTransform(pos, angleInRad);
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));//TODO add texture
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
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
    public void act(float delta) {
        body.getBody().setLinearVelocity((float) Math.cos(Math.toDegrees(body.getBody().getAngle())) * speed * delta,
            (float) Math.sin(Math.toDegrees(body.getBody().getAngle())) * speed * delta);
    }

    public void destroy(){
        Gdx.app.log("Bullet", "deleted");
        stage.getActors().removeValue(this, false);
    }
}
