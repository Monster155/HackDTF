package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import ru.itlab.hackdtf.Utils.Utils;


public class Player {
    public static final Vector2 SIZE = new Vector2(1, 1);
    public static final Vector2 stickCS = new Vector2(0,0);
    public static final int speedOfPlayer = 250;
    public static final int maxLives = 2;
    public static int lives = maxLives;


    public Fixture body;
    public Texture texture;

    public Player(World world) {
        body = Utils.createBox(world, new Vector2(50, 50), SIZE.x, SIZE.y, false, "player", (short) -2);
        texture = new Texture("player.png"); // Наш игрок
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,
                body.getBody().getPosition().x - SIZE.x/2,
                body.getBody().getPosition().y - SIZE.y/2,
                SIZE.x,
                SIZE.y);
    }

    public void update(float delta){
        float x = stickCS.x, y = stickCS.y;
        //for tests on computer
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            x--;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            x++;

        if(Gdx.input.isKeyPressed(Input.Keys.W))
            y++;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            y--;

        body.getBody().setLinearVelocity(delta*speedOfPlayer*x, delta*speedOfPlayer*y);
    }

    public void damaged(){
        lives--;
        texture.dispose();
        if(lives<=0){
            Gdx.app.log("Game", "Game over");
        }
    }
}
