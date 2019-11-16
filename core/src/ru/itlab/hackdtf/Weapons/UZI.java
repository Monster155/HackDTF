package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class UZI extends Gun {

    int x, y, bulletCount, timeBetweenShoot = 600;
    float time;
    double sum = 0;

    Texture gunTexture;
    ArrayList<Bullet> bullets;

    public UZI(int x, int y, int bulletCount, CharacterParent characterParent) {
        super(x, y, characterParent);
        gunTexture = new Texture("");
        this.bulletCount = bulletCount;
        bullets = new ArrayList<>();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bullets.add(new Bullet(this));
        bulletCount -= 3;
        while(sum < 0.1) sum += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (Gdx.input.isTouched() && bulletCount != 0 && time - TimeUtils.nanosToMillis(TimeUtils.nanoTime()) >= timeBetweenShoot) {
            for (int i = 0; i < 3; i++) {
                update(Gdx.graphics.getDeltaTime());
                sum = 0;
                for (Bullet b:
                        bullets) {
                    b.draw(batch, parentAlpha);
                    b.remove();
                }
            }

        }
    }
}
