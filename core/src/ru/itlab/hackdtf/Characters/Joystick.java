package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Joystick extends Actor {

    Circle backCircle, stickCircle;
    Texture stickTexture, backTexture;
    public float sin = 0, cos = 0;

    public Joystick() {
        stickTexture = new Texture(Gdx.files.internal("joystick/stick.png"));//TODO add joystick pictures
        backTexture = new Texture(Gdx.files.internal("joystick/back.png"));
        stickCircle = new Circle(75, 75, 30);
        backCircle = new Circle(75, 75, 75);
        setBounds(0, 0, 640, 360);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backCircle.setPosition(x, y);
                stickCircle.setPosition(x, y);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                stickCircle.setPosition(x, y);
            }
        });
    }

    @Override
    public void act(float delta) {
        float x = stickCircle.x - backCircle.x;
        float y = stickCircle.y - backCircle.y;
        float radius = (float) (Math.sqrt(x * x + y * y));

        if (!backCircle.contains(stickCircle.x, stickCircle.y)) {
            stickCircle.x = backCircle.radius * x / radius + backCircle.x;
            stickCircle.y = backCircle.radius * y / radius + backCircle.y;
        }

        sin = backCircle.radius / (backCircle.radius * y / radius + backCircle.y);
        cos = backCircle.radius / (backCircle.radius * x / radius + backCircle.x);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(backTexture,
                backCircle.x - backCircle.radius, backCircle.y - backCircle.radius,
                2 * backCircle.radius, 2 * backCircle.radius);
        batch.draw(stickTexture,
                stickCircle.x - stickCircle.radius, stickCircle.y - stickCircle.radius,
                2 * stickCircle.radius, 2 * stickCircle.radius);
    }

}
