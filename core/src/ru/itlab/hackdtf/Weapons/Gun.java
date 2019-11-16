package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public abstract class Gun extends Actor {

    int x, y;
    Texture gunTexture;
    float time;

    CharacterParent parent;

    public Gun(int x, int y){
        this.x = x;
        this.y = y;
        time = TimeUtils.nanosToMillis(TimeUtils.nanoTime());

        parent = new CharacterParent();
    }

    @Override
    public void act(float delta) {
        time = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(gunTexture, x, y, gunTexture.getWidth(), gunTexture.getHeight());
    }

    public double getCharacterParentRotation(){
        return parent.getRotation();
    }
}
