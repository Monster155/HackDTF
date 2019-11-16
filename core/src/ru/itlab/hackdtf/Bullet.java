package ru.itlab.hackdtf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    int x;
    int y;
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();
    int speed;
    double angle;
    Texture imgBullet;

    Bullet(int speed) {
        this.x = 0;
        this.y = 120;
        this.speed = speed;
        imgBullet = new Texture(Gdx.files.internal("strawberry(1).png"));
        //угол полета пули
        angle = Math.random() * 30;
    }

    //перемещение пули и её направление
    public void update() {
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
    }

    //рисуем пулю
    public void render(SpriteBatch batch) {
        update();
        batch.draw(imgBullet, x, y, 16, 16);
    }

    public void dispose(){
        imgBullet.dispose();
    }

}
