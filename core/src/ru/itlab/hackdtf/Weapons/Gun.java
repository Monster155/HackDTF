package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

import ru.itlab.hackdtf.Characters.Enemy;
import ru.itlab.hackdtf.Characters.Player;
import ru.itlab.hackdtf.Screens.GameScreen;

public class Gun extends Actor {

    Texture texture;
    int type;
    Stage stage;
    World world;
    float reload, reloadTime = 500;
    public int bulletCount;

    public boolean isDropped = false;
    Player player;
    boolean isSlowLast;

    int sumOfBullets = 0, maxSumOfBullets;
    float timeForNextBullet;
    float scatter = 0;

    public Vector2 pos = new Vector2(0, 0);
    public Vector2 size;
    public float angleInDeg = 0;

    public Gun(Stage stage, World world, int type, boolean isEnemy, Player player) {
        this.player = player;
        this.type = type;
        chooseGun();
        if (isEnemy) bulletCount = 99999;
        this.stage = stage;
        this.world = world;
        player.guns.add(this);
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
        reloadTime += delta;
        if(isSlowLast != GameScreen.isSlow)
            useBraking(GameScreen.isSlow);
        if (reloadTime > timeForNextBullet && sumOfBullets < maxSumOfBullets - 1 && sumOfBullets >= 0) {
            reloadTime = 0;
            sumOfBullets++;
            stage.addActor(new Bullet((float) Math.toRadians(angleInDeg + randRot()), pos, world, stage));
        }
    }

    public void useBraking(boolean isSlow) {
        if (isSlow){
            reload *= GameScreen.braker/5;
            reloadTime *= GameScreen.braker/5;
            timeForNextBullet *= GameScreen.braker/5;
        }
        else {
            reload /= GameScreen.braker/5;
            reloadTime /= GameScreen.braker/5;
            timeForNextBullet /= GameScreen.braker/5;
        }
        isSlowLast = isSlow;
    }

    public void shoot() {
        if (reloadTime > reload && sumOfBullets == maxSumOfBullets - 1) {
            reloadTime = 0;
            sumOfBullets = 0;
            stage.addActor(new Bullet((float) Math.toRadians(angleInDeg + randRot()), pos, world, stage));
        }
    }

    public float randRot() {
        return (float) (-scatter / 2 + Math.random() * scatter);
    }

    public void destroy() {
        player.guns.removeValue(this, true);
        stage.getActors().removeValue(this, true);
    }

    public void chooseGun() {
        switch (type) {
            case 1:
                texture = new Texture(Gdx.files.internal("enemy.png"));
                size = new Vector2(30, 10);
                reload = 0.6f;
                maxSumOfBullets = 1;
                timeForNextBullet = 0;
                bulletCount = (int) (2 + TimeUtils.millis() % 4);
                scatter = 25;
                break;
            case 2:
                texture = new Texture(Gdx.files.internal("enemy.png"));
                size = new Vector2(20, 20);
                reload = 1;
                maxSumOfBullets = 3;
                timeForNextBullet = 0.2f;
                bulletCount = (int) (10 + TimeUtils.millis() % 6);
                scatter = 50;
                break;
            case 3:
                texture = new Texture(Gdx.files.internal("enemy.png"));
                size = new Vector2(10, 30);
                reload = 1;
                maxSumOfBullets = 4;
                timeForNextBullet = 0;
                bulletCount = (int) (16 + TimeUtils.millis() % 9);
                scatter = 90;
                break;
            case 4:
                texture = new Texture(Gdx.files.internal("enemy.png"));
                size = new Vector2(50, 20);
                reload = 0.2f;
                maxSumOfBullets = 1;
                timeForNextBullet = 0;
                bulletCount = (int) (10 + TimeUtils.millis() % 11);
                scatter = 70;
                break;

            //TODO set texture, bullet count, size, reload
        }
        sumOfBullets = maxSumOfBullets - 1;
    }

    public void updatePos(Vector2 pos, float angleInDeg, float size) {
        this.pos.x = (float) (pos.x + (size / 2) * Math.cos(Math.toRadians(angleInDeg)));
        this.pos.y = (float) (pos.y + (size / 2) * Math.sin(Math.toRadians(angleInDeg)));
        this.angleInDeg = angleInDeg;
    }
}
