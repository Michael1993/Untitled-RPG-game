package com.joapet99.console.startup;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.joapet99.console.states.Console;
import com.joapet99.console.states.Console2;
import com.joapet99.console.states.Console3;
import com.joapet99.tile.TileMap;

public class Main extends StateBasedGame{
	public static boolean debug = false;
	
	public static final int consoleState = 0;
	public static final int console2State = 1;
	public static final int console3State = 2;
	
	public static int columns = 80; //how many columns there are gonna be
	public static int rows = 25; //how many rows there are gonna be
	public static int standardTileWidth = 10; //width of tile
	public static int standardTileHeight = 15; // height of tile
	
	public static int gameWidth; //the width of the screen
	public static int gameHeight; //the height of the screen
	
	public Main(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException { //Here you can add new states of the game
		this.addState(new Console());//The id of this state is what getId() returns inside the Console class. getId() returns consoleState.
		                             //If you change consoleState then everything will automatically change after that cause i don't use hard typed numbers
		this.addState(new Console2());
		this.addState(new Console3());
		this.enterState(consoleState); //This enters the console state.
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new Main("Console"));
		app.setTargetFrameRate(60); //This sets the framerate for the game
		app.setShowFPS(false); //By default slick2d automatically renders the fps in the top left corner. Setting this to false disables that.
		gameWidth = TileMap.getMaxX(columns, standardTileWidth) + 1; //Sets the gamewidth to make the tiles fit.
		gameHeight = TileMap.getMaxY(rows, standardTileHeight) + 1; //Sets the gameheight to make the tiles fit
		app.setDisplayMode(gameWidth, gameHeight, false);
		app.start();//Starts the screen
	}

}
