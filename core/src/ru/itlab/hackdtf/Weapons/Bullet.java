package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Bullet{
    int x, y;
    int speed;

    Texture imgBullet;
    Gun gun;

    Bullet(Gun gun) {
        this.gun = gun;
        this.speed = 10;
        this.x = gun.x;
        this.y = gun.y;
    }

    public void update(float delta) {
        x += speed * Math.cos(gun.getCharacterParentRotation()) * delta;
       y += speed * Math.sin(gun.getCharacterParentRotation()) * delta;
    }

    public void draw(Batch batch) {
        batch.begin();
        update(Gdx.graphics.getDeltaTime());
        batch.draw(imgBullet, x, y, imgBullet.getWidth(), imgBullet.getHeight());
        batch.end();
    }

    public void dispose() {
        imgBullet.dispose();
    }

}
