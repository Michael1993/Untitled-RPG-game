package ru.yegorf1.console;

import com.badlogic.gdx.Gdx;

public class Game implements Runnable  {
	@Override
	public void run() {		
		Console.addAnimation(new Animation() {			
			@Override
			public float getDelay() { return 0.01f; }
			
			@Override
			public void frame() {
				Console.setCursorPosition(0, 0);
				Console.writeLine("Window width: " + Gdx.graphics.getWidth());
				Console.writeLine("Window height: " + Gdx.graphics.getHeight());
				Console.writeLine("Char width: " + (Gdx.graphics.getWidth() / Console.BufferWidth));
				Console.writeLine("Char height: " + (Gdx.graphics.getHeight() / Console.BufferHeight));
			}
		});
	}

}
