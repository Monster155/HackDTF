package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class UZI extends Gun {

    int x, y, bulletCount, timeBetweenShoot = 600;
    float time;
    double sum = 0;

    Texture gunTexture;
    ArrayList<Bullet> bullets;

    public UZI(int x, int y, int bulletCount) {
        super(x, y);
        gunTexture = new Texture("");
        this.bulletCount = bulletCount;
        bullets = new ArrayList<>();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bullets.add(new Bullet(this));
        bulletCount -= 3;
        while(sum < 0.1) sum += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (Gdx.input.isTouched() && bulletCount != 0 && time - TimeUtils.nanosToMillis(TimeUtils.nanoTime()) >= timeBetweenShoot) {
            for (int i = 0; i < 3; i++) {
                act(parentAlpha);
                double sum = 0;
                for (Bullet b:
                        bullets) {
                    b.draw(batch, parentAlpha);
                    b.remove();
                }
            }

        }
    }
}
