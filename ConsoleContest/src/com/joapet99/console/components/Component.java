package com.joapet99.console.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.joapet99.tile.*;

/**
 * This class represents the classical gui components ported to ascii components.
 * @author joakim
 */
public abstract class Component{
	Vector2f position;
	int width;
	int height;
	TileMap tileMap;
	
	/**
	 * 
	 * @param position - the wanted coordinate on the tilemap
	 * @param width - how many columns the component will take
	 * @param height - how many rows the optionpane will take
	 */
	public Component(Vector2f position, int width, int height){
		this.position = position;
		this.width = width;
		this.height = height;
	}
	/**
	 * Renders the component. Usually called every render
	 * @param gc - the container of the game
	 * @param sbg - the game(statebased)
	 * @param gr - needed to draw and render things
	 */
	public abstract void render(GameContainer gc, StateBasedGame sbg, Graphics gr);
	
	/**
	 * 
	 * @param gc - the container of the game
	 * @param sbg - the game(statebased)
	 * @param delta - a number that when multiplied with something will make things move with the same speed on slow computers as fast ones
	 */
	public abstract void update(GameContainer gc, StateBasedGame sbg, int delta);
	
	/**
	 * Sets the tilemap for the component
	 * @param tileMap - the tilemap
	 */
	public void setTileMap(TileMap tileMap){
		this.tileMap = tileMap;
	}
	
	/**
	 * 
	 * @return
	 * gets the current tilemap for the component
	 */
	public TileMap getTileMap(){
		return tileMap;
	}
}
