package com.joapet99.console.components;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.gea.behavior.Action;

import com.joapet99.tile.*;

/**
 * This class represents a rectangle that can easily be added options to it. 
 * The options will automatically be arranged after eachothers.
 * @author joakim
 *
 */
public class OptionPane extends Component{
	ArrayList<Option> options;
	Vector2f positionForNewOption;
	
	/**
	 * 
	 * @param position - the wanted coordinate position in the tilemap
	 * @param width - how many columns the optionpane will take
	 * @param height - how many rows the optionpane will take 
	 */
	public OptionPane(Vector2f position, int width, int height) {
		super(position, width, height);
		options = new ArrayList<Option>();
		positionForNewOption = new Vector2f(position.x + 1, position.y + 1);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
		//Renders the frame around that represents the bound of the optionpane
		for(int j = (int)position.y; j < (int)position.y + height;j++){ //For every row til height is exceeded
			if(j == (int)position.y || j == (int)position.y + height - 1){ //If it is the top line or bottom line of the optionpane
				StringBuilder builder = new StringBuilder();
				builder.append('+');
				for(int i = (int)position.x + 1; i < position.x + width - 1;i++){
					builder.append('-');
				}
				builder.append('+');
				tileMap.setText(new Vector2f(this.position.x, j), builder.toString());
			}else{ //Means that it is something in between 
				tileMap.getTile((int)position.x, j).setChar('|');
				tileMap.getTile((int)position.x + width - 1, j).setChar('|');
			}
		}
		
		//Renders all the options
		for(Option o : options){
			o.render(gc, sbg, gr);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		//Updates all options
		for(Option o : options){
			o.update(gc, sbg, delta);
		}
	}
	
	/**
	 * 
	 * @param optionText - the text displayed as the option
	 * @param activationKey - the key that activates the option
	 * @param actionOnActivation - the action that gets performed when activated
	 */
	public void addOption(String optionText, int activationKey, Action actionOnActivation){
		Option o = new Option(positionForNewOption.copy());
		o.setOptionText(optionText);
		o.setActivationKey(activationKey);
		o.setActionOnActivated(actionOnActivation);
		this.options.add(o);
		
		positionForNewOption.y++;
	}
	
	/**
	 * 
	 * @param optionText - the text displayed as the option
	 * @return
	 * the option with the given optiontext
	 */
	public Option getOption(String optionText){
		for(Option o : options){
			if(o.getOptionText().equals(optionText)){
				return o;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 * all the options in the optionpane
	 */
	public ArrayList<Option> getOptions(){
		return options;
	}
	
	public void setTileMap(TileMap tileMap){
		super.setTileMap(tileMap);
		//Also changes the tilemap for all the options
		for(Option o : options){
			o.setTileMap(tileMap);
		}
	}
}
