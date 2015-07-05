package com.joapet99.console.states;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.gea.behavior.Action;
import com.gea.gui.Drama;
import com.joapet99.console.components.Component;
import com.joapet99.console.components.OptionPane;
import com.joapet99.console.startup.Main;
import com.joapet99.tile.Tile;
import com.joapet99.tile.TileMap;

public class Console extends BasicGameState{
	public static TileMap tileMap;
	ArrayList<Component> components;
	Drama drama;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		tileMap = new TileMap(Main.columns, Main.rows, Main.standardTileWidth, Main.standardTileHeight);
		components = new ArrayList<Component>();
		
		//Initialises all the optionpanes
		OptionPane upperLeftPane = new OptionPane(new Vector2f(0,0),40,13);
		OptionPane bottomLeftPane = new OptionPane(new Vector2f(0,12),40,13);
		OptionPane upperRightPane = new OptionPane(new Vector2f(40,0),40,13);
		OptionPane bottomRightPane = new OptionPane(new Vector2f(40,12),40,13);
		OptionPane continuePane = new OptionPane(new Vector2f(40,22),40,3);
		
		//Adds all the optionpanes to the list of components
		components.add(upperLeftPane);
		components.add(bottomLeftPane);
		components.add(upperRightPane);
		components.add(bottomRightPane);
		components.add(continuePane);
		
		//Adds options to the upper left optionpane
		upperLeftPane.addOption("Option 1", Input.KEY_1, new Action("Option1"){
			@Override
			public void execute() { //Things inside here will be executed when the option is activated by the given key
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 1.");
				lines.add("Option 1 is the first option you can choose in both total and in the upper left optionpane.");
				lines.add("You can try other options by pressing other digits");
				drama.loadLines(lines); //Loads the lines into the drama instance.
				
				//Sets the position of the drama instance
				Tile t = tileMap.getTile(1, 4);
				drama.setPosition(new Vector2f(t.getX(), t.getY()));
			}
		});
		upperLeftPane.addOption("Option 2", Input.KEY_2, new Action("Option2"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 2.");
				lines.add("Option 2 is the second option and the option in the middle.");
				lines.add("You can try other options by pressing other digits");
				drama.loadLines(lines);
				Tile t = tileMap.getTile(1, 4);
				drama.setPosition(new Vector2f(t.getX(), t.getY()));
			}
		});
		upperLeftPane.addOption("Option 3", Input.KEY_3, new Action("Option3"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 3.");
				lines.add("Option 3 is the third option and the last option you can choose from the upper left optionpane.");
				lines.add("You can try other options by pressing other digits");
				drama.loadLines(lines);
				Tile t = tileMap.getTile(1, 4);
				drama.setPosition(new Vector2f(t.getX(), t.getY()));
			}
		});
		
		//Adds options to the bottom left optionpane
		bottomLeftPane.addOption("Option 4", Input.KEY_4, new Action("Option4"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 4.");
				lines.add("Option 4 is the fourth option, the only and the last option you can choose from the bottom left optionpane.");
				lines.add("You can try other options by pressing other digits");
				drama.loadLines(lines);
				Tile t = tileMap.getTile(1, 14);
				drama.setPosition(new Vector2f(t.getX(), t.getY()));
			}
		});
		
		//Adds option to the upper right optionpane
		upperRightPane.addOption("Option 5", Input.KEY_5, new Action("Option5"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 5.");
				lines.add("Option 5 is the fifth option and the first option you can choose from the upper right optionpane.");
				lines.add("You can try other options by pressing other digits");
				drama.loadLines(lines);
				Tile t = tileMap.getTile(41, 3);
				drama.setPosition(new Vector2f(t.getX(), t.getY()));
			}
		});
		upperRightPane.addOption("Option 6", Input.KEY_6, new Action("Option6"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 6.");
				lines.add("Option 6 is the sixth option and the last option you can choose from the upper right optionpane.");
				lines.add("You can try other options by pressing other digits");
				drama.loadLines(lines);
				Tile t = tileMap.getTile(41, 3);
				drama.setPosition(new Vector2f(t.getX(), t.getY()));
			}
		});
		
		//Adds options to the bottom right optionpane
		bottomRightPane.addOption("Option 7", Input.KEY_7, new Action("Option7"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 7.");
				lines.add("Option 7 is the seventh option and the first option you can choose from the bottom right optionpane.");
				lines.add("You can try other options by pressing other digits");
				drama.loadLines(lines);
				Tile t = tileMap.getTile(41, 16);
				drama.setPosition(new Vector2f(t.getX(), t.getY()));
			}
		});
		bottomRightPane.addOption("Option 8", Input.KEY_8, new Action("Option8"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 8.");
				lines.add("Option 8 is the eigth option and the second option you can choose from the bottom right optionpane.");
				lines.add("You can try other options by pressing other digits");
				drama.loadLines(lines);
				Tile t = tileMap.getTile(41, 16);
				drama.setPosition(new Vector2f(t.getX(), t.getY()));
			}
		});
		bottomRightPane.addOption("Option 9", Input.KEY_9, new Action("Option9"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 9.");
				lines.add("Option 9 is the last option in both total and the bottom right optionpane");
				lines.add("You can try other options by pressing other digits");
				drama.loadLines(lines);
				Tile t = tileMap.getTile(41, 16);
				drama.setPosition(new Vector2f(t.getX(), t.getY()));
			}
		});
		
		//Adds continue option to the continuePane located in the bottom right corner
		continuePane.addOption("Continue", Input.KEY_C, new Action("ContinueToConsole2"){
			@Override
			public void execute() {
				((StateBasedGame)arguments[0]).enterState(Main.console2State); //Changes the state to next console state
			}
		});
		
		//For the continue option to be able to change state it needs to have an instance of StateBasedGame. The action class has a method
		//named setArguments where you can freely set the arguments to whatever you want. Then in the execute method you can use the arguments.
		continuePane.getOption("Continue").getActionOnActivated().setArguments(new Object[]{sbg});
		
		//Initialises drama from GameEngine Alpha(my own engine, very tiny though). Check the docs for more information.
		Tile t = tileMap.getTile(1, 4);
		drama = new Drama(new Vector2f(t.getX(),t.getY()), TileMap.getMaxX(38, Main.standardTileWidth) - t.getX(),
				                                           TileMap.getMaxY(20, Main.standardTileHeight) - t.getY());
		
		//Connects all components to the tilemap
		for(Component c : components){
			c.setTileMap(tileMap);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
			throws SlickException {
		//Renders the tilemap
		tileMap.render(gr);
		
		//Renders all the components
		for(Component c : components){
			c.render(gc, sbg, gr);
		}
		
		//If the drama class is not done with it's job, continue rendering it
		if(!drama.isDone)
			drama.render(gc, sbg, gr);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		//Updates all the components
		for(Component c : components){
			c.update(gc, sbg, delta);
		}
		
		//If the drama class is not done with it's job, continue updating it.
		if(!drama.isDone)
			drama.update(gc, sbg, delta);
		
		if(gc.getInput().isKeyPressed(Input.KEY_SPACE)){ //Checks if space has been pressed
			if(drama.doneTyping){ //If drama is done typing, go to the next line
				drama.nextLine();
				drama.instantDisplay = false; //Disable the instantDisplay. When true it makes the text instantly appear instead of getting typed
			}else if(!drama.doneTyping && !drama.isDone){
				drama.instantDisplay = true; //Enable the instantDisplay. When true it makes the text instantly appear instead of getting typed.
			}
		}
	}

	@Override
	public int getID() {
		return Main.consoleState; //Returns the correct id chosen in the main file. This can be changed without causing issues(unless it collides with another id)
	}

}
