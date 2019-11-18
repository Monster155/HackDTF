package ru.itlab.hackdtf.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;

public class Main extends Game {

	GameScreen gs;
	Music music;
	
	@Override
	public void create () {
		music = Gdx.audio.newMusic(Gdx.files.internal("music/musicForGame.mp3"));
		music.setLooping(true);
		music.play();
		gs = new GameScreen(music);
//		setScreen(gs);
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		music.dispose();
	}
}
