package com.joapet99.console.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.gea.behavior.Action;
import com.gea.gui.Drama;
import com.joapet99.console.components.OptionPane;
import com.joapet99.console.startup.*;
import com.joapet99.tile.Tile;
import com.joapet99.tile.TileMap;

public class Console3 extends BasicGameState{
	OptionPane highlightOptions;
	TileMap tileMap;
	Drama drama;
	int currentOptionIndex;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		tileMap = new TileMap(Main.columns, Main.rows, Main.standardTileWidth, Main.standardTileHeight);
		highlightOptions = new OptionPane(new Vector2f(0,0),20,12);
		highlightOptions.addOption("Just", Input.KEY_1, new Action("Option1"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 1.");
				lines.add("I congratulate you to have finished this demo");
				lines.add("Now this console will exit.");
				lines.add("Bye :(");
				lines.add("EXITGAME");
				drama.loadLines(lines);
			}
		});
		highlightOptions.addOption("Random", Input.KEY_2, new Action("Option2"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 2.");
				lines.add("I congratulate you to have finished this demo");
				lines.add("Now this console will exit.");
				lines.add("Bye :(");
				lines.add("EXITGAME");
				drama.loadLines(lines);
			}
		});
		highlightOptions.addOption("Options", Input.KEY_3, new Action("Option3"){
			@Override
			public void execute() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("You have chosen option 3.");
				lines.add("I congratulate you to have finished this demo");
				lines.add("Now this console will exit.");
				lines.add("Bye :(");
				lines.add("EXITGAME");
				drama.loadLines(lines);
				if(highlightOptions.getOptions().get(currentOptionIndex).isHighlighted()){
					highlightOptions.getOptions().get(currentOptionIndex).disableHighlightning();
				}
			}
		});
		highlightOptions.setTileMap(tileMap);
		
		Tile t = tileMap.getTile(0, 3);
		drama = new Drama(new Vector2f(t.getX() + 10,t.getY() + 5), 150, 100);
		drama.addCommand("EXITGAME", new Action("Exit Game"){
			@Override
			public void execute() {
				System.exit(0);
			}
		});
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
			throws SlickException {
		Input in = gc.getInput();
		highlightOptions.render(gc, sbg, gr);
		if(!highlightOptions.getOptions().get(currentOptionIndex).isHighlighted()){
			highlightOptions.getOptions().get(currentOptionIndex).highlight(); //highlights the option
		}else{
			if(drama.getLines() != null){
				highlightOptions.getOptions().get(currentOptionIndex).disableHighlightning(); //removes the highlight
			}
		}
		if(in.isKeyPressed(Input.KEY_UP)){
			if(currentOptionIndex > 0){
				highlightOptions.getOptions().get(currentOptionIndex--).disableHighlightning();
			}
		}
		if(in.isKeyPressed(Input.KEY_DOWN)){
			if(currentOptionIndex < highlightOptions.getOptions().size() - 1){
				highlightOptions.getOptions().get(currentOptionIndex++).disableHighlightning();
			}
		}
		if(in.isKeyPressed(Input.KEY_SPACE) || in.isKeyPressed(Input.KEY_ENTER)){
			if(drama.getLines() != null){
				if(!drama.doneTyping){
					drama.instantDisplay = true;
				}else{
					drama.nextLine();
					drama.instantDisplay = false;
				}
			}
			else{
				highlightOptions.getOptions().get(currentOptionIndex).getActionOnActivated().execute();
			}
		}
		tileMap.render(gr);
		if(!drama.isDone)
			drama.render(gc, sbg, gr);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		highlightOptions.update(gc, sbg, delta);
		if(!drama.isDone)
			drama.update(gc, sbg, delta);
	}

	@Override
	public int getID() {
		return Main.console3State;
	}

}
