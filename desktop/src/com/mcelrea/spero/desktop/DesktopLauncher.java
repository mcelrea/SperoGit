package com.mcelrea.spero.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mcelrea.spero.Spero;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Spero";
		config.width = 1921;
		config.height = 1080;
		config.fullscreen = true;
		new LwjglApplication(new Spero(), config);
	}
}
