package com.mcelrea.spero;

import com.badlogic.gdx.Game;
import com.mcelrea.screens.GamePlay;

public class Spero extends Game {

	@Override
	public void create() {
		setScreen(new GamePlay());
		
	}
	
}
