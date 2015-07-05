package ru.yegorf1.console;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;	
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public class Console extends ApplicationAdapter implements InputProcessor {
	public static final int BufferWidth = 80; 
	public static final int BufferHeight = 25;

	private static Color ForegroundColor = Color.WHITE;
	private static Color BackgroundColor = Color.BLACK;
	
	private static int cursorPosition = 0;
	
	// I added russian letters
	private static BitmapFont consoleFont;
	private static final String CHARS_TO_TYPE = FreeTypeFontGenerator.DEFAULT_CHARS 
			+ "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
	
	private static ConsoleCharacter[] buffer = new ConsoleCharacter[BufferWidth * BufferHeight];
	
	private SpriteBatch batch;	
	private ShapeRenderer shapeRenderer; // Renders background
	
	private static Queue<ConsoleKey> charactersPressed = new LinkedList<ConsoleKey>();
	private static boolean waitingForChar		= false;	// Is another thread is waitng for key typed
	public static boolean  isLeftCtrlPressed	= false,	// Is left control pressed now
						   isRightCtrlPressed	= false,	// Is right control pressed now
						   isShiftPressed		= false;	// Is any shift pressed now
	
	private static List<AnimPair> animations = new ArrayList<AnimPair>(); 

	private static final Object syncObject = new Object(); 	// Synchronized when working with buffer, colors, etc.
	
	@Override
	public void create () {
		generateFont();		
		clear();
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		Gdx.input.setInputProcessor(this);
		
		Thread gameThread = new Thread(new Game());
		gameThread.start();
		
	}
	
	/**
	 * Generating BitmapFont from true type font
	 */
	private static void generateFont() {
		if (consoleFont != null) {
			consoleFont.dispose();
		}
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("consola.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		parameter.size = Gdx.graphics.getHeight() / BufferHeight;
		parameter.characters = CHARS_TO_TYPE;
		consoleFont = generator.generateFont(parameter);
		generator.dispose();
		
		consoleFont.setColor(getForegroundColor());	
	}
	
	/**
	 * Clearing buffer and filling it with spaces
	 */
	public static void clear() {
		synchronized (syncObject) {
			for (int i = 0; i < buffer.length; i++) {
				buffer[i] = new ConsoleCharacter(' ');
			}
		}
	}
	
	/**
	 * Calling automatical when windows resizes
	 */
	public void resize(int width, int height) {
		Matrix4 matrix = new Matrix4();
		matrix.setToOrtho2D(0, 0, width, height);
		batch.setProjectionMatrix(matrix);
		
		generateFont();
    }
	
	public void dispose() {
		consoleFont.dispose();
		batch.dispose();
	}

	/**
	 * Calling each frame
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		int charWidth  = Gdx.graphics.getWidth() / BufferWidth,
			charHeight = Gdx.graphics.getHeight() / BufferHeight;
		
		float delta = Gdx.graphics.getDeltaTime();
		for (AnimPair pair : animations) {
			if (pair.animation.getDelay() > 0) {
				pair.timer += delta;
				while (pair.timer >= pair.animation.getDelay()) {
					pair.timer -= pair.animation.getDelay();
					pair.animation.frame();
				}
			} else {
				pair.animation.frame();
			}
		}
		
		// Background rendering takes less than 1 ms..
		synchronized (syncObject) {
			batch.begin();
			shapeRenderer.begin(ShapeType.Filled);
			for (int i = 0; i < buffer.length; i++) {
				int charX = i % BufferWidth * charWidth,
					charY = Gdx.graphics.getHeight() - (i / BufferWidth + 1) * charHeight;

				shapeRenderer.setColor(buffer[i].backgroundColor);
				shapeRenderer.rect(charX, charY, charWidth, charHeight);
			}
			shapeRenderer.end();
			batch.end();
			// I can't use shapeRenderer and font rendering in same batch operation 
			batch.begin();
			for (int i = 0; i < buffer.length; i++) {
				int charX = i % BufferWidth * charWidth,
					charY = Gdx.graphics.getHeight() - i / BufferWidth * charHeight;

				consoleFont.setColor(buffer[i].foregroundColor);
				consoleFont.draw(batch, buffer[i].character + "", charX, charY, charWidth, charHeight, true);
			}
			batch.end();
		}

	}

	/**
	 * Sets cursor position. Coordinate system starts in top-left angle
	 * @param x position from left
	 * @param y position from top
	 */
	public static void setCursorPosition(int x, int y) {
		if (x < 0 || x >= BufferWidth) {
			throw new IllegalArgumentException("x must be non-negative and be less than ConsoleGame.BufferWidth");
		}
		if (y < 0 || y >= BufferHeight) {
			throw new IllegalArgumentException("y must be non-negative and be less than ConsoleGame.BufferHeight");
		}
		synchronized (syncObject) {
			cursorPosition = x + BufferWidth * y;
		}
	}
	
	/**
	 * Get the {@link #ForegroundColor} value
	 * ForegroundColor controls foreground color of chars 
	 * @return foregroundColor Current ForegroundColor value
	 */
	public static Color getForegroundColor() {
		return ForegroundColor;
	}

	/**
	 * Set the {@link #ForegroundColor} value
	 * ForegroundColor controls foreground color of chars 
	 * @param foregroundColor New ForegroundColor value
	 */
	public static void setForegroundColor(Color foregroundColor) {
		ForegroundColor = foregroundColor;
	}
	
	/**
	 * Get the {@link #BackgroundColor} value
	 * BackgroundColor controls background color of chars 
	 * @return backgroundColor Current BackgroundColor value
	 */
	public static Color getBackgroundColor() {
		return BackgroundColor;
	}

	/**
	 * Set the {@link #BackgroundColor} value
	 * BackgroundColor controls background color of chars 
	 * @param backgroundColor New BackgroundColor value
	 */
	public static void setBackgroundColor(Color backgroundColor) {
		BackgroundColor = backgroundColor;
	}

	/**
	 * Sets {@link #ForegroundColor} and {@link #BackgroundColor}.
	 * Foreground color will be white and background color will be black
	 */
	public static void resetColors() {
		setForegroundColor(Color.WHITE);
		setBackgroundColor(Color.BLACK);
	}

	/**
	 * Writes string to console starting from current cursor position
	 * @param message string to write
	 */
	public static void write(String message) {
		int i = 0;
		
		synchronized (syncObject) {
			while (cursorPosition < buffer.length && i < message.length()) {
				char character = message.charAt(i);
				
				if (character == '\n' || character == '\r') {
					cursorPosition += BufferWidth - (cursorPosition % BufferWidth);
				} else {
					buffer[cursorPosition] = new ConsoleCharacter(character);
					cursorPosition++;
				}
				i++;
			}
		}
	}
	
	/**
	 * Calling {@link Object#toString()} and writing it to console
	 * @param obj Object that will be converted to string
	 */
	public static void write(Object obj) {
		write(obj.toString());
	}
	
	/**
	 * Writes string to console and starts new line
	 * @param message string to write
	 */
	public static void writeLine(String message) {
		write(message + '\n');
	}
	
	/**
	 * Writes art to console starting from current cursor position
	 * @param art art to write to console
	 */
	public static void writeArt(String art) {
		String[] lines = art.split("\\r?\\n");
		int xPosition, yPosition;
		
		synchronized (syncObject) {
			xPosition = cursorPosition % BufferWidth;
			yPosition = cursorPosition / BufferWidth;
		}
		
		for (int i = 0; i < lines.length; i++) {
			if (yPosition + i >= BufferHeight) {
				return;
			}
			setCursorPosition(xPosition, yPosition + i);
			write(lines[i]);
		}
	}
	
	/**
	 * Writes string to console char by char with delay between two chars
	 * @param message string to write
	 * @param delay delay between two chars in milliseconds
	 */
	public static void writeLineWithTiming(String message, int delay) throws InterruptedException {
		for (char c : (message + '\n').toCharArray()) {
			write(c);
			Thread.sleep(delay);
		}
	}
	
	/**
	 * Waits until a key is pressed or uses one from pressed before
	 * @return {@link ConsoleKey}
	 */
	public static ConsoleKey readKey() {
		ConsoleKey result = new ConsoleKey('\0');
		synchronized (charactersPressed) {
			if (charactersPressed.isEmpty()) {
				try {
					waitingForChar = true;
					charactersPressed.wait();
					result = charactersPressed.poll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				result = charactersPressed.poll();
			}
			
			waitingForChar = false;
		}
		
		return result;
	}
	
	/**
	 * Reads keys while Enter wasn't pressed
	 * @return {@link String} that was read, that contains only typeable characters
	 */
	public static String readLine() {
		String result = "";
		ConsoleKey key = readKey();
		
		while (key.character != '\r') {
			if (CHARS_TO_TYPE.contains(key.character +"")) {
				result += key.character;
			}
			key = readKey();
		}
		
		return result;
	}
	
	public static void addAnimation(Animation animation) {
		animations.add(new AnimPair(animation));
	}
	
	public static void removeAnimation(Animation animation) {
		int index = -1;
		for (int i = 0; i < animations.size(); i++) {
			if (animation == animations.get(i).animation) {
				index = i;
				break;
			}
		}
		if (index >= 0) {
			animations.remove(index);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		isLeftCtrlPressed	= keycode == Keys.CONTROL_LEFT;
		isRightCtrlPressed	= keycode == Keys.CONTROL_RIGHT;
		isShiftPressed		= keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT;
		return false; 
	}

	@Override
	public boolean keyUp(int keycode) { 
		isLeftCtrlPressed	&= keycode != Keys.CONTROL_LEFT;
		isRightCtrlPressed	&= keycode != Keys.CONTROL_RIGHT;
		isShiftPressed 		&= keycode != Keys.SHIFT_LEFT && keycode != Keys.SHIFT_RIGHT;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		synchronized (charactersPressed) {
			if (character != 0) {
				charactersPressed.add(new ConsoleKey(character));
				if (CHARS_TO_TYPE.contains(character + "")) {
					write(character);
				}
				if (waitingForChar) {
					charactersPressed.notifyAll();
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

	@Override
	public boolean mouseMoved(int screenX, int screenY) { return false; }

	@Override
	public boolean scrolled(int amount) { return false; }	
	
	public static Object getSyncobject() {
		return syncObject;
	}

	private static class ConsoleCharacter {
		public char character;
		public Color foregroundColor;
		public Color backgroundColor;
		
		public ConsoleCharacter(char character) {
			this.character = character;
			this.foregroundColor = Console.getForegroundColor();
			this.backgroundColor = Console.getBackgroundColor();
		}
	}
	
	private static class AnimPair {
		public Animation animation;
		public float timer;
		
		public AnimPair(Animation animation) {
			this.animation = animation;
			timer = 0f;
		}
	}
}
