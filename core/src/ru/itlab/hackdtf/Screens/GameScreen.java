package ru.itlab.hackdtf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ru.itlab.hackdtf.Characters.Joystick;
import ru.itlab.hackdtf.Characters.Player;

public class GameScreen implements Screen {

    Stage stage;
    StretchViewport viewport;
    Player player;
    Joystick joystick;
    World world;

    @Override
    public void show() {
        world = new World(new Vector2(0, 0), true);
        viewport = new StretchViewport(640, 360);
        stage = new Stage(viewport);
        joystick = new Joystick();
        stage.addActor(joystick);
        player = new Player(joystick, world);
        stage.addActor(player);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        world.step(delta, 6, 2);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
