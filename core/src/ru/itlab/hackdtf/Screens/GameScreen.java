package ru.itlab.hackdtf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ru.itlab.hackdtf.Characters.*;
import ru.itlab.hackdtf.*;
import ru.itlab.hackdtf.Weapons.Bullet;
import ru.itlab.hackdtf.Weapons.Gun;

public class GameScreen implements Screen {

    Music music;

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
    Array<Fixture> mapBody;
    int level[][];
    int i = -1, j = 0;
    Fixture walls[] = new Fixture[4];

    History history;
    boolean isSecondRoom = false;

    public GameScreen(Music music){
        this.music = music;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, 0), true);
        mapBody = new Array<>();

        setWorldContactListener();

        b2ddr = new Box2DDebugRenderer();
        viewport = new StretchViewport(640, 360);
        stage = new Stage(viewport);

        history = new History();
        stage.addActor(history);

        level = Graph_map.getLevel();
        findStart();
        makeWalls();
        resetWalls();

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

        history.setText(0);
        history.setDraw(true);
    }

    @Override
    public void render(float delta) {
        world.step(delta, 6, 2);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player.body.getBody().getPosition().x > 640) {
            //go to right level
            if (level[i].length > j + 1)
                if (level[i][j + 1] != 0) {
                    j++;
                    loadNewRoom();
                }
        } else if (player.body.getBody().getPosition().x < 0) {
            //go to left level
            if (j > 0)
                if (level[i][j - 1] != 0) {
                    j--;
                    loadNewRoom();
                }
        } else if (player.body.getBody().getPosition().y > 360) {
            //go to up level
            if (i > 0)
                if (level[i - 1][j] != 0) {
                    i--;
                    loadNewRoom();
                }
        } else if (player.body.getBody().getPosition().y < 0) {
            //go to down level
            if (level.length > i + 1)
                if (level[i + 1][j] != 0) {
                    i++;
                    loadNewRoom();
                }
        }
        tmr.setView((OrthographicCamera) stage.getCamera());
        tmr.render();

        stage.act();
        stage.draw();

//        b2ddr.render(world, stage.getCamera().combined);

        if (player.health <= 0) {
            //first ending
            if (level[i][j] == -2) {
                history.setText(1);
            } else {
                history.setText(4);
            }
            history.setDraw(true);
            findStart();
            loadNewRoom();
            player.health = 2;
        }

        if (level[i][j] == -2 && player.enemies.size == 0) {
            //second ending
            history.setText(2);
            history.setDraw(true);
        }

        if(isSecondRoom){
            history.setText(3);
            history.setDraw(true);
            //TODO before fight in last room you can choose: escape or fight
        }
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

    public void myDispose() {
        map.dispose();
        tmr.dispose();
        for (Fixture f : mapBody) {
            world.destroyBody(f.getBody());
        }
    }

    private void setWorldContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA(), fb = contact.getFixtureB();
                if (fa.getUserData() == null || fa.getUserData() == null)
                    return;
                if (fa.getUserData().equals("player") && fb.getUserData().equals("enemy"))
                    for (Actor actor : stage.getActors()) {
                        try {
                            if (((Enemy) actor).equals(fb)) {
                                ((Enemy) actor).damaged();
                                break;
                            }
                        } catch (Exception e) {
                        }
                    }
                else if (fb.getUserData().equals("player") && fa.getUserData().equals("enemy"))
                    for (Actor actor : stage.getActors()) {
                        try {
                            if (((Enemy) actor).equals(fa)) {
                                ((Enemy) actor).damaged();
                                break;
                            }
                        } catch (Exception e) {
                        }
                    }
                if (fa.getUserData().equals("pBullet") && !(fb.getUserData().equals("player") || fb.getUserData().equals("pBullet"))) {
                    Gdx.app.log("Bullet", "touched A");
                    for (Actor a : stage.getActors()) {
                        try {
                            if (((Bullet) a).body.equals(fa))
                                ((Bullet) a).inGame = false;
                        } catch (Exception e) {
                        }
                    }
                    for (Enemy e : player.enemies) {
                        if (e.body.equals(fb))
                            e.damaged();
                    }
                } else if (fb.getUserData().equals("pBullet") && !(fa.getUserData().equals("player") || fa.getUserData().equals("pBullet"))) {
                    Gdx.app.log("Bullet", "touched B");
                    for (Actor a : stage.getActors()) {
                        try {
                            if (((Bullet) a).body.equals(fb))
                                ((Bullet) a).inGame = false;
                        } catch (Exception e) {
                        }
                    }
                    for (Enemy e : player.enemies) {
                        if (e.body.equals(fa))
                            e.damaged();
                    }
                }

                if (fa.getUserData().equals("eBullet") && !(fb.getUserData().equals("enemy") || fb.getUserData().equals("eBullet"))) {
                    Gdx.app.log("Enemy bullet", "touched A");
                    for (Actor a : stage.getActors()) {
                        try {
                            if (((Bullet) a).body.equals(fa))
                                ((Bullet) a).inGame = false;
                        } catch (Exception e) {
                        }
                    }
                    if (fb.getUserData().equals("player"))
                        player.health--;
                } else if (fb.getUserData().equals("eBullet") && !(fa.getUserData().equals("enemy") || fa.getUserData().equals("eBullet"))) {
                    Gdx.app.log("Enemy bullet", "touched B");
                    for (Actor a : stage.getActors()) {
                        try {
                            if (((Bullet) a).body.equals(fb))
                                ((Bullet) a).inGame = false;
                        } catch (Exception e) {
                        }
                    }
                    if (fa.getUserData().equals("player"))
                        player.health--;
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

    public void findStart() {
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[i].length; j++) {
                if (level[i][j] == -1) {
                    this.i = i;
                    this.j = j;
                    break;
                }
            }
            if (this.i != -1)
                break;
        }
    }

    public void makeWalls() {
        map = new TmxMapLoader().load("levels/map1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 4);
        mapBody = TiledObjectUtil.buildBuildingsBodies(map, world);

        walls[0] = CreateFixture.createBox(world, new Vector2(5, -5), new Vector2(640, 10), true, "wall", (short) 4);
        walls[1] = CreateFixture.createBox(world, new Vector2(645, 5), new Vector2(10, 360), true, "wall", (short) 4);
        walls[2] = CreateFixture.createBox(world, new Vector2(5, 365), new Vector2(640, 10), true, "wall", (short) 4);
        walls[3] = CreateFixture.createBox(world, new Vector2(-5, 5), new Vector2(10, 360), true, "wall", (short) 4);
    }

    public void loadNewRoom() {
        myDispose();
        if (level[i][j] == -1) {
            map = new TmxMapLoader().load("levels/map1.tmx");
        } else if (level[i][j] == -2) {
            map = new TmxMapLoader().load("levels/map5.tmx");
            isSecondRoom = true;
        } else {
            map = new TmxMapLoader().load("levels/map"+((int)(Math.random()*2)+2)+".tmx");
        }
        tmr = new OrthogonalTiledMapRenderer(map, 4);
        mapBody = TiledObjectUtil.buildBuildingsBodies(map, world);
        player.body.getBody().setTransform(320, 180, player.body.getBody().getAngle());
        if(player.health > 0)stage.addActor(new Enemy(stage, world, player));
        if(level[i][j] == -2){
            stage.addActor(new Enemy(stage, world, player));
            stage.addActor(new Enemy(stage, world, player));
        }
        resetWalls();
    }

    public void resetWalls() {
        walls[0].getBody().setTransform(5, -5, 0);
        walls[1].getBody().setTransform(645, 5, 0);
        walls[2].getBody().setTransform(5, 365, 0);
        walls[3].getBody().setTransform(-5, 5, 0);
        if (level.length > i + 1) {
            if (level[i + 1][j] != 0) {
                walls[0].getBody().setLinearVelocity(5, -45);
                walls[0].getBody().setTransform(5, -45, 0);
            }
        }
        if (i > 0) {
            if (level[i - 1][j] != 0) {
                walls[2].getBody().setLinearVelocity(5, 405);
                walls[2].getBody().setTransform(5, 405, 0);
            }
        }
        if (level[i].length > j + 1) {
            if (level[i][j + 1] != 0) {
                walls[1].getBody().setLinearVelocity(685, 5);
                walls[1].getBody().setTransform(685, 5, 0);
            }
        }
        if (j > 0) {
            if (level[i][j - 1] != 0) {
                walls[3].getBody().setLinearVelocity(-45, 5);
                walls[3].getBody().setTransform(-45, 5, 0);
            }
        }
        Gdx.app.log("Pos", i + " " + j);
    }
}
