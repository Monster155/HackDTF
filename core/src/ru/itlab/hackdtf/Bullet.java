package ru.itlab.hackdtf;

import com.badlogic.gdx.Gdx;

public class Bullet {
    int x;
    int y;
    int widthScreen = Gdx.graphics.getWidth();
    int heightScreen = Gdx.graphics.getHeight();
    int width;
    int height;
    double angle;

    Bullet() {
        this.x = 0;
        this.y = 120;
        this.width = 25;
        this.height = 25;

        //угол полета пули
        angle = Math.random() * 30;
    }

    //перемещение пули и её направление
    public void update(int speed) {
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
    }

}
