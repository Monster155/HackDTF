package ru.itlab.hackdtf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MiniMap extends Actor {

    Texture[][] levelTextures;
    Texture playerPosition;
    Vector2 pos;

    public MiniMap(int[][] level) {
        levelTextures = new Texture[level.length][level[0].length];
        playerPosition = new Texture(Gdx.files.internal("enemy.png"));
        pos = new Vector2(0, 0);
        for (int i = 0; i < level.length; i++)
            for (int j = 0; j < level[i].length; j++)
                if (level[i][j] != 0)
                    levelTextures[i][j] = new Texture(Gdx.files.internal("start.png"));
                else levelTextures[i][j] = new Texture(Gdx.files.internal("back.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < levelTextures.length; i++) {
            for (int j = 0; j < levelTextures[i].length; j++) {
                batch.draw(levelTextures[i][j], 585 + 5 * (j - pos.y), 305 - 5 * (i - pos.x), 5, 5);
                if (i == pos.x && j == pos.y)
                    batch.draw(playerPosition, 585, 305, 5, 5);
            }
        }
    }

    @Override
    public void act(float delta) {

    }

    public void changePos(int i, int j) {
        pos = new Vector2(i, j);
    }
}
