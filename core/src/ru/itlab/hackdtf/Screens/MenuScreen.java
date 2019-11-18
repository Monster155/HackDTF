package ru.itlab.hackdtf.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen implements Screen {
    SpriteBatch batch;
    OrthographicCamera camera;

    Texture startButtonTexture;
    Texture authorsButtonTexture;
    Texture optionsButtonTexture;
    Texture timeExplorerTexture;
    Texture backGroundTexture;

    Sprite startButtonSprite;
    Sprite authorsButtonSprite;
    Sprite optionsButtonSprite;
    Sprite timeExplorerSprite;
    Sprite backGroundSprite;

    //TODO
    private static float BUTTON_RESIZE_FACTOR = 800f;
    private static float START_VERT_POSITION_FACTOR = 3.3f;
    private static float AUTHOR_VERT_POSITION_FACTOR = 4.2f;
    private static float OPTIONS_VERT_POSITION_FACTOR = 5.7f;
    private static float TIMEEXPLORER_VERT_POSITION_FACTOR = 2.5f;

    Main main;
    Vector3 temp = new Vector3();

    public MenuScreen(Main main) {
        this.main = main;
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(false);
        batch = new SpriteBatch();

        startButtonTexture = new Texture(Gdx.files.internal("start.png"));
        authorsButtonTexture = new Texture(Gdx.files.internal("authors.png"));
        optionsButtonTexture = new Texture(Gdx.files.internal("options.png"));
        timeExplorerTexture = new Texture(Gdx.files.internal("TimeExplorer.png"));
        backGroundTexture = new Texture(Gdx.files.internal("background.jpg"));

        startButtonSprite = new Sprite(startButtonTexture);
        authorsButtonSprite = new Sprite(authorsButtonTexture);
        optionsButtonSprite = new Sprite(optionsButtonTexture);
        timeExplorerSprite = new Sprite(timeExplorerTexture);
        backGroundSprite = new Sprite(backGroundTexture);


        startButtonSprite.setSize(startButtonSprite.getWidth() * (width / BUTTON_RESIZE_FACTOR) / 3, startButtonSprite.getHeight() * (width / BUTTON_RESIZE_FACTOR) / 3);
        authorsButtonSprite.setSize(authorsButtonSprite.getWidth() * (width / BUTTON_RESIZE_FACTOR) / 3, authorsButtonSprite.getHeight() * (width / BUTTON_RESIZE_FACTOR) / 3);
        optionsButtonSprite.setSize(optionsButtonSprite.getWidth() * (width / BUTTON_RESIZE_FACTOR) / 3, optionsButtonSprite.getHeight() * (width / BUTTON_RESIZE_FACTOR) / 3);
        timeExplorerSprite.setSize(timeExplorerSprite.getWidth() * (width / BUTTON_RESIZE_FACTOR) / 3, timeExplorerSprite.getHeight() * (width / BUTTON_RESIZE_FACTOR) / 3);
        backGroundSprite.setSize(width, height);


        startButtonSprite.setPosition((width / 2f - startButtonSprite.getWidth() / 2), width / START_VERT_POSITION_FACTOR);
        authorsButtonSprite.setPosition((width / 2f - authorsButtonSprite.getWidth() / 2), width / AUTHOR_VERT_POSITION_FACTOR);
        optionsButtonSprite.setPosition((width / 2f - optionsButtonSprite.getWidth() / 2), width / OPTIONS_VERT_POSITION_FACTOR);
        timeExplorerSprite.setPosition((width / 2f - timeExplorerSprite.getWidth() / 2), width / TIMEEXPLORER_VERT_POSITION_FACTOR);
        backGroundSprite.setAlpha(1);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        //отрисовка игровых объектов
        batch.begin();
        backGroundSprite.draw(batch);
        startButtonSprite.draw(batch);
        authorsButtonSprite.draw(batch);
        optionsButtonSprite.draw(batch);
        timeExplorerSprite.draw(batch);
        handleTouch();
        batch.end();
    }

    void handleTouch() {
        if (Gdx.input.justTouched()) {
            temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(temp);
            float touchX = temp.x;
            float touchY = temp.y;
            if ((touchX >= startButtonSprite.getX())
                    && touchX <= (startButtonSprite.getX() + startButtonSprite.getWidth())
                    && (touchY >= startButtonSprite.getY())
                    && touchY <= (startButtonSprite.getY() + startButtonSprite.getHeight())) {
                main.setScreen(main.gs);
            }
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
        dispose();
    }

    @Override
    public void dispose() {
        startButtonTexture.dispose();
        authorsButtonTexture.dispose();
        optionsButtonTexture.dispose();
        timeExplorerTexture.dispose();
        batch.dispose();
    }
}
