package ru.itlab.hackdtf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
    String start, end1, end2, end3, end4, boss;
    public boolean isDraw = false;
    Texture texture;

    public History() {
        font = new BitmapFont(Gdx.files.internal("font/RuEn.fnt"));
        glyphLayout = new GlyphLayout();
        texture = new Texture(Gdx.files.internal("back.png"));
        start = "Включается телевизор, после чего диктор начинает вещание:\n \"Всем известно, что роботы были созданы, как основной помощник человека в борьбе за выживание. Выдерживающие любые нагрузки и температурные условия, они не раз доказывали свою преданность делу, помогая исследовать бесконечные просторы мироздания. Сложно представить: когда-то люди боялись, что система может выйти из строя и человеку будет причинён вред. Времена \"основателей\" давно позади. Time Explorer - крупнейший производитель не только военной, но и гражданской промышленности - гарантирует Вам стабильность и качество своих изобретений...\"";
        end1 = "Вы были в шаге от победы, но сегодня удача не на вашей стороне...\n" +
                "Ваша планета захвачена. Человеческий род истреблен.\n" +
                "Настала новая эра.\n"+
                "*Это 1 из 3 концовок*";
        end2 = "Поздравляю!\n" +
                "Вы смогли отключить питание роботов и спасти человечество!\n" +
                "Можете возвращаться на своё рабочее место.\n"+
                "*Это 1 из 3 концовок*";
        end3 = "Вы смогли спастись, но долго ли продлится ваше спокойствие?\n"+"" +
                "С каждым днем враг становится все сильней, и кто знает, когда вам придется увидеть его вновь...\n"+
                "*Это 1 из 3 концовок*";
        end4 = "Вы погибли, теперь вам придется начать с начала...\n"+
                "*Это самая скучная концовка...*";
        boss = "*Битва с боссом*\n *Можете сбежать - вернутся в предыдущую комнату*\n*Или попытаться одолеть босса*";
        setBounds(0, 0, 640, 360);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setDraw(false);
                return true;
            }
        });
        font.getData().setScale(0.55f);
        //TODO пропишите каждый String, концовки идут в порядке как на катинке или в GameScreen
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDraw) {
            batch.draw(texture, 0, 0, 640, 360);
            font.draw(batch, glyphLayout, 30, 360);
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
            case 4:
                glyphLayout.setText(font, end4, Color.WHITE, 580, Align.center, true);
                break;
            case 5:
                glyphLayout.setText(font, boss, Color.WHITE, 580, Align.center, true);
                break;
        }
    }

    public void setDraw(boolean is) {
        isDraw = is;
        if (is) toFront();
        else toBack();
    }
}
