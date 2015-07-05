package com.joapet99.console.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.gea.behavior.Action;

/**
 * This class represents an option that the player can choose. Recommended to be used through the OptionPane class
 * @author joakim
 *
 */
public class Option extends Component{
	int activationKey;
	Action action;
	String optionText;
	boolean highlighted = false;
	
	/**
	 * 
	 * @param position - the wanted position for the option on the tilemap
	 */
	public Option(Vector2f position) {
		super(position, 0, 0);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
		tileMap.setText(new Vector2f(position.x, position.y), "[" + Input.getKeyName(activationKey) + "]" + optionText);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		Input in = gc.getInput();
		if(highlighted){
			tileMap.setFillForTiles(Color.white, (int)position.y, (int)position.x, (int)position.x + ("[" + Input.getKeyName(activationKey) + "]" + optionText).length());
			tileMap.setFontColorForTiles(Color.black, (int)position.y, (int)position.x, (int)position.x + ("[" + Input.getKeyName(activationKey) + "]" + optionText).length());
		}
		if(in.isKeyPressed(activationKey)){
			action.execute();
		}
	}
	
	/**
	 * Sets the key that activates the option
	 * @param key - the key that activates the option
	 */
	public void setActivationKey(int key){
		this.activationKey = key;
	}
	
	/**
	 * Sets the action that is performed when the option is activated by the associated key
	 * @param action - the action that gets performed when activated
	 */
	public void setActionOnActivated(Action action){
		this.action = action;
	}
	
	/**
	 * 
	 * @return
	 * the key that activates the option
	 */
	public int getActivationKey(){
		return activationKey;
	}
	
	/**
	 * 
	 * @return
	 * the action that gets performed when activated
	 */
	public Action getActionOnActivated(){
		return action;
	}
	
	/**
	 * Sets the text that is displayed as the option
	 * @param text - the text that is displayed as the option
	 */
	public void setOptionText(String text){
		this.optionText = text;
	}
	
	/**
	 * 
	 * @return
	 * the text that is displayed as the option
	 */
	public String getOptionText(){
		return this.optionText;
	}
	
	/**
	 * Highlights the option(white)
	 */
	public void highlight(){
		this.highlighted = true;
	}
	
	/**
	 * Removes the highlightning of the option
	 */
	public void disableHighlightning(){
		this.highlighted = false;
		tileMap.setFillForTiles(Color.black, (int)position.y, (int)position.x, (int)position.x + ("[" + Input.getKeyName(activationKey) + "]" + optionText).length());
		tileMap.setFontColorForTiles(Color.white, (int)position.y, (int)position.x, (int)position.x + ("[" + Input.getKeyName(activationKey) + "]" + optionText).length());
	}
	
	/**
	 * 
	 * @return
	 * true - if the option is highlighted
	 * false - if it's not highlighted
	 */
	public boolean isHighlighted(){
		return highlighted;
	}
}
