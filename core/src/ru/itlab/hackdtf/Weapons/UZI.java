package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class UZI extends Gun {

    int x, y, bulletCount, timeBetweenShoot = 600, timeBetweenShootThreePools = 100;
    float time;

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
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (Gdx.input.isTouched() && bulletCount != 0 && time - TimeUtils.nanosToMillis(TimeUtils.nanoTime()) >= timeBetweenShoot) {
            act(parentAlpha);
            for (Bullet b :
                    bullets) {
                if (>=timeBetweenShootThreePools) {
                    b.draw(batch, parentAlpha);
                }
                b.remove();
            }
        }
    }
}
