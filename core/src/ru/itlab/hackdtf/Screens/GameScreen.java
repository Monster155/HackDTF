package ru.itlab.hackdtf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ru.itlab.hackdtf.Characters.*;
import ru.itlab.hackdtf.*;
import ru.itlab.hackdtf.Weapons.Gun;

public class GameScreen implements Screen {

    public static boolean isSlow = false;
    public static int braker = 40;

    Stage stage;
    StretchViewport viewport;
    Player player;
    Joystick joystick;
    ActionButton actionButton;

    World world;
    Box2DDebugRenderer b2ddr;
    TiledMap map;
    OrthogonalTiledMapRenderer tmr;

    @Override
    public void show() {
        world = new World(new Vector2(0, 0), true);

        setWorldContactListener();
        map = new TmxMapLoader().load("levels/testLevel.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 2.5f);
        TiledObjectUtil.buildBuildingsBodies(map, world);

        b2ddr = new Box2DDebugRenderer();
        viewport = new StretchViewport(640, 360);
        stage = new Stage(viewport);

        joystick = new Joystick();
        stage.addActor(joystick);

        player = new Player(stage, joystick, world);
        stage.addActor(player);

        actionButton = new ActionButton(player);
        stage.addActor(actionButton);

        Gun gun = new Gun(stage, world, 3, false, player);
        gun.pos = new Vector2(400, 200);
        gun.isDropped = true;
        stage.addActor(gun);

        Gun gun2 = new Gun(stage, world, 2, false, player);
        gun2.pos = new Vector2(200, 200);
        gun2.isDropped = true;
        stage.addActor(gun2);

        stage.addActor(new Enemy(stage, world, player));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        world.step(delta, 6, 2);
        tmr.setView((OrthographicCamera) stage.getCamera());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        tmr.render();
        b2ddr.render(world, stage.getCamera().combined);
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
        stage.dispose();
        tmr.dispose();
        map.dispose();
    }

    private void setWorldContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA(), fb = contact.getFixtureB();
                if (fa.getUserData() == null || fa.getUserData() == null)
                    return;
                if ((fa.getUserData().equals("player") && fb.getUserData().equals("enemy")))
                    for (Actor actor : stage.getActors()) {
                        try {
                            if (((Enemy) actor).equals(fb)) {
                                ((Enemy) actor).damaged();
                                break;
                            }
                        } catch (Exception e) {
                            Gdx.app.log(actor.getName() + "", "is not an Enemy");
                        }
                    }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}
