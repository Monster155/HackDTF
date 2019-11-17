package ru.itlab.hackdtf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

public class History extends Actor {

    BitmapFont font;
    GlyphLayout glyphLayout;
    String start, end1, end2, end3;
    public boolean isDraw = false;
    Texture texture;

    public History() {
        font = new BitmapFont(Gdx.files.internal("font/RuEn.fnt"));
        glyphLayout = new GlyphLayout();
        texture = new Texture(Gdx.files.internal("back.png"));
        start = "Start";
        end1 = "1";
        end2 = "2";
        end3 = "3";
        setBounds(0, 0, 640, 360);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setDraw(false);
                return true;
            }
        });
        //TODO пропишите каждый String, концовки идут в порядке как на катинке или в GameScreen
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDraw) {
            batch.draw(texture, 0, 0, 640, 360);
            font.draw(batch, glyphLayout, 30, 180 - glyphLayout.height / 2);
        }
    }

    @Override
    public void act(float delta) {
    }

    public void setText(int id) {
        switch (id) {
            case 0:
                glyphLayout.setText(font, start, Color.WHITE, 580, Align.center, true);
                break;
            case 1:
                glyphLayout.setText(font, end1, Color.WHITE, 580, Align.center, true);
                break;
            case 2:
                glyphLayout.setText(font, end2, Color.WHITE, 580, Align.center, true);
                break;
            case 3:
                glyphLayout.setText(font, end3, Color.WHITE, 580, Align.center, true);
                break;
        }
    }

    public void setDraw(boolean is) {
        isDraw = is;
        if (is) toFront();
        else toBack();
    }
}
