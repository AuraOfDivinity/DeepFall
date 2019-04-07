package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helpers.GameManager;
import scenes.Gameplay;
import scenes.MainMenu;

public class GameMain extends Game {
	private SpriteBatch batch;

	//Its's a best practice to create only spritebatch and pass it onto other classes as required. Creating multiple sprite batches adds too much load to the system.

	@Override
	public void create () {
		batch = new SpriteBatch();
		GameManager.getInstance().initializeGameData();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render(); //Enables other classes to render onto the screen

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
