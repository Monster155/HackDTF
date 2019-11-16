package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import ru.itlab.hackdtf.Characters.Player;


public class ActionButton extends Actor {
    Texture shootButton;
    private int x = 500, y = 100, size = 25;

    public ActionButton(final Player player) {
        setBounds(x, y, size * 2, size * 2);
        shootButton = new Texture(Gdx.files.internal("actionButton.png"));
        this.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.shoot();
            }
        });

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(shootButton, 500, 50, 100, 100);
    }

}
