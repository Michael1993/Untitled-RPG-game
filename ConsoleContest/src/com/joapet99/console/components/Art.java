package com.joapet99.console.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * An instance of this class will represent ascii art on the tilemap when rendered.
 * @author joakim
 *
 */
public class Art extends Component{
	ArrayList<String> lines; //All the lines in the art
	
	/**
	 * 
	 * @param position - the wanted position for the art
	 */
	public Art(Vector2f position) {
		super(position, 0, 0);
		lines = new ArrayList<String>();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
		//Draws the art on the screen
		for(int i = 0; i < lines.size();i++){
			if(tileMap.getTile((int)position.x, (int)position.y + i) != null){
				tileMap.setText(new Vector2f(position.x, position.y + i), lines.get(i));
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		
	}
	
	/**
	 * Loads given file as it was a normal text file.
	 * @param f - the file you want to load
	 */
	public void loadArtFromFile(File f){
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			while((line = br.readLine()) != null){//As long as there are more lines in the file. Add them to the arraylist
				lines.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the coordinate position of the art on the tilemap
	 * @param position
	 */
	public void setPosition(Vector2f position){
		this.position = position;
	}
	
	/**
	 * 
	 * @return
	 * the position of the art on the tilemap
	 */
	public Vector2f getPosition(){
		return position;
	}
	
	/**
	 * 
	 * @return
	 * the height of the art
	 */
	public int getHeight(){
		return lines.size();
	}
}
