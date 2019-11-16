package ru.itlab.hackdtf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pistol extends Actor {
    int x;
    int y;
    //int speed;
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    Pistol(){
        this.x = (int)(Math.random()*width);
        this.y = (int)(Math.random()*height);
    }

    //пистолет
    public void change(){
        x = (int)(Math.random()*width);
        y = (int)(Math.random()*height);
    }

}
