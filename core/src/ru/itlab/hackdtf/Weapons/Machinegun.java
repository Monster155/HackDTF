package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class Machinegun extends Gun {
    int x, y, bulletCount, timeBetweenShoot = 200;
    float time;
    double sum = 0;

    Texture gunTexture;
    ArrayList<Bullet> bullets;

    public Machinegun(int x, int y, CharacterParent parent) {
        super(x, y, parent);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bullets.add(new Bullet(this));
        bulletCount --;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (Gdx.input.isTouched() && bulletCount != 0 && time - TimeUtils.nanosToMillis(TimeUtils.nanoTime()) >= timeBetweenShoot) {
            update(Gdx.graphics.getDeltaTime());
            for (Bullet b :
                    bullets) {
                b.draw(batch, parentAlpha);
                b.remove();
            }
        }
    }
}
