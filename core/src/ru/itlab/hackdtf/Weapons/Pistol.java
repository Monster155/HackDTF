package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;
import ru.itlab.hackdtf.Characters.*;

import java.util.ArrayList;

public class Pistol extends Gun {

    int x, y, bulletCount, timeBetweenShoot = 600;
    float time;

    Texture gunTexture;
    ArrayList<Bullet> bullets;

    public Pistol(int x, int y, int bulletCount, CharacterParent characterParent) {
        super(x, y, characterParent);
        gunTexture = new Texture("");
        this.bulletCount = bulletCount;
        bullets = new ArrayList<>();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bullets.add(new Bullet(this));
        bulletCount--;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(Gdx.input.isTouched() && bulletCount != 0 && time - TimeUtils.nanosToMillis(TimeUtils.nanoTime()) >= timeBetweenShoot) {
            update(Gdx.graphics.getDeltaTime());
            bullets.get(0).draw(batch);
            bullets.remove(0);
        }
    }
}
