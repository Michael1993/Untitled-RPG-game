package com.joapet99.console.states;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.gea.behavior.Action;
import com.gea.time.CountDown;
import com.joapet99.console.startup.Main;
import com.joapet99.tile.TileMap;
import com.joapet99.console.components.*;

public class Console2 extends BasicGameState{
	TileMap tileMap;
	Art art;
	OptionPane continuePane;
	CountDown cd = new CountDown((long)0);

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		tileMap = new TileMap(Main.columns, Main.rows, Main.standardTileWidth, Main.standardTileHeight);
		
		art = new Art(new Vector2f(0,Main.rows));
		art.loadArtFromFile(new File("src/com/joapet99/console/assets/Sword_Small.txt")); //Loads art from given file
		art.setTileMap(tileMap);
		
		continuePane = new OptionPane(new Vector2f(40,22),40,3);
		continuePane.addOption("Continue", Input.KEY_C, new Action("ContinueToConsole3"){
			@Override
			public void execute() {
				((StateBasedGame)arguments[0]).enterState(Main.console3State); //Changes the state to next console state
			}
		});
		continuePane.getOption("Continue").getActionOnActivated().setArguments(new Object[]{sbg});
		continuePane.setTileMap(tileMap);
		
		cd.start(); //Check javadoc
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
			throws SlickException {
		tileMap.clearAll(); //Clears the whole tilemap for characters
		art.render(gc, sbg, gr); // Renders the art
		continuePane.render(gc, sbg, gr);
		tileMap.render(gr); //Renders the tilemap
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		art.update(gc, sbg, delta); //Updates the art
		cd.update(); //Check javadoc
		if(cd.isDone()){ //If the countdown is done, 
			art.setPosition(new Vector2f(art.getPosition().x, art.getPosition().y - 1)); //Moves the art one tile up
			cd.start(); //Restarts the countdown
		}
		continuePane.update(gc, sbg, delta);
	}

	@Override
	public int getID() {
		return Main.console2State;
	}

}
