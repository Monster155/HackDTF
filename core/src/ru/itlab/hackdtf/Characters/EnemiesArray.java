package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class EnemiesArray extends Actor {

    Array<Enemy> enemyArray;

    public EnemiesArray() {
        super();
        enemyArray = new Array<>();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (Enemy e : enemyArray) {
            e.draw(batch, parentAlpha);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Enemy e : enemyArray) {
            e.act(delta);
        }
    }

    public void add(Enemy enemy){
        enemyArray.add(enemy);
    }
}
