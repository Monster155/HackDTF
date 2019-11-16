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
    private int x = 535, y = 30, size = 75;

    public ActionButton(final Player player) {
        setBounds(x, y, size, size);
        shootButton = new Texture(Gdx.files.internal("actionButton.png"));
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //player.shoot(); //TODO gun shoot
                return super.touchDown(event, x, y, pointer, button);
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
        batch.draw(shootButton, x, y, size, size);
    }

}
