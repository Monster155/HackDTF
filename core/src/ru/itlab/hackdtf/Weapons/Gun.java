package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Gun extends Actor {

    Texture texture;
    Vector2 size;
    int type;
    Stage stage;
    World world;

    public Vector2 pos = new Vector2(0, 0);
    public float angleInDeg = 0;

    public Gun(Stage stage, World world, int type) {
        this.type = type;
        chooseGun();
        this.stage = stage;
        this.world = world;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,
                pos.x,
                pos.y,
                0, //center for rotation and scaling x
                0, //center for rotation and scaling y
                size.x, size.y,
                1, -1, //scale from center
                angleInDeg,
                0, 0, //coordinates in image file
                texture.getWidth(), //size in image file
                texture.getHeight(),
                false, false);
    }

    @Override
    public void act(float delta) {

    }

    public void shoot() {
        stage.addActor(new Bullet((float) Math.toRadians(angleInDeg), pos, world, stage));
    }

    public void destroy() {
        stage.getActors().removeValue(this, false);
    }

    public void chooseGun() {
        switch (type) {
            case 1:
                texture = new Texture(Gdx.files.internal("enemy.png"));
                size = new Vector2(30, 10);
                //TODO set texture, bullet count, size
        }
    }

    public void updatePos(Vector2 pos, float angleInDeg, float size) {
        this.pos.x = (float) (pos.x + (size / 2) * Math.cos(Math.toRadians(angleInDeg)));
        this.pos.y = (float) (pos.y + (size / 2) * Math.sin(Math.toRadians(angleInDeg)));
        this.angleInDeg = angleInDeg;
    }
}
