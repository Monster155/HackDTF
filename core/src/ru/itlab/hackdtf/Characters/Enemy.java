package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.itlab.hackdtf.CreateFixture;
import ru.itlab.hackdtf.Weapons.Gun;

public class Enemy extends Actor {

    Texture texture;
    Fixture body;
    int speed, health = 2;
    Player player;
    boolean dead = false;
    public int bulletCount = 1000;//TODO create guns
    Gun gun;
    Stage stage;

    public Enemy(Stage stage, World world, Player player) {
        this.stage = stage;
        this.player = player;
        speed = player.speed;
        texture = new Texture(Gdx.files.internal("enemy.png"));
        body = CreateFixture.createCircle(world, new Vector2(320, 180), 25, false, "enemy", (short) 2);
        body.getBody().setTransform(new Vector2(200, 180), 0);
        gun = new Gun(stage, world, 1);
        stage.addActor(gun);
        player.enemies.add(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.getBody().setLinearVelocity((float) Math.cos(Math.toDegrees(body.getBody().getAngle())) * speed * delta,
                (float) Math.sin(Math.toDegrees(body.getBody().getAngle())) * speed * delta);
        gun.updatePos(body.getBody().getPosition(), (float) Math.toDegrees(body.getBody().getAngle()), body.getShape().getRadius());
        //body.getBody().getTransform().setRotation((float) Math.atan2(x, y));
        //TODO logic of enemies (use player + logic of UFOB enemies)
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

    public void damaged() {
        health--;
        if (health <= 0) {
            destroy();
        }
    }

    public void destroy() {
        player.enemies.removeValue(this, false);
        stage.getActors().removeValue(this, false);
    }

    @Override
    public float getRotation() {
        return body.getBody().getAngle();
    }
}
