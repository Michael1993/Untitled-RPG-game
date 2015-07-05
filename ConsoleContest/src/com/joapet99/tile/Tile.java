package com.joapet99.tile;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import com.joapet99.console.startup.Main;

public class Tile{
	int tileX; //The x position in the tilemap
	int tileY; //The y position in the tilemap
	int tileWidth; //The width of the tile
	int tileHeight; //The height of the tile
	
	Font font; //The font that is rendering the char
	
	Character charInTile; //The character that this tile holds
	Color fill; //Fillcolor for the tile
	Color fontColor; //The color that the char will be written in
	
	/**
	 * 
	 * @param tileX - x coordinate in tilemap
	 * @param tileY - y coordinate in tilemap
	 * @param tileWidth - width of the tile
	 * @param tileHeight - height of the tile
	 */
	public Tile(int tileX, int tileY, int tileWidth, int tileHeight){
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.font = TileMap.font; //Gets the default font from the tilemap
	}
	
	/**
	 * Renders the tile
	 * @param gr - needed for drawing and such things
	 */
	public void render(Graphics gr){
		if(Main.debug){ //If in debug mode, draw a rectangle around the tile
			gr.drawRect(getX(), getY(), tileWidth, tileHeight);
		}
		if(fill != null){
			gr.setColor(fill);//Sets the color of the graphics to the fill color
			gr.fillRect(getX(), getY(), tileWidth, tileHeight); //Fills given rectangle
			gr.setColor(Color.white); //Sets the color to the default
		}
		if(charInTile != null){ //If the tile actually has been assigned a char
			if(fontColor != null){ //If the tile holds a font color, draw the char with the given font color
				font.drawString(getX(),getY() - 2, String.valueOf(charInTile.toString()), fontColor);
			}else{//If not then just draw the string with default color(white)
				font.drawString(getX(),getY() - 2, String.valueOf(charInTile.toString()));
			}
		}
	}
	
	/**
	 * 
	 * @return
	 * the x posiiton on the screen
	 */
	public int getX(){
		return tileX * tileWidth; //Multiplies the x coordinate in the tilemap with the width of the tile to get the x
	}
	
	/**
	 * 
	 * @return
	 * the y posiiton on the screen
	 */
	public int getY(){
		return tileY * tileHeight; //Multiplies the y coordinate in the tilemap with the height of the tile to get the y
	}
	
	/**
	 * 
	 * @return
	 * the x position on the tilemap
	 */
	public int getTileX(){
		return tileX;
	}
	
	/**
	 * 
	 * @return
	 * the y position on the tilemap
	 */
	public int getTileY(){
		return tileY;
	}
	
	/**
	 * Sets the character for the tile
	 * @param c - the wanted char
	 */
	public void setChar(char c){
		this.charInTile = c;
	}
	
	/**
	 * Sets the font for the tile
	 * @param f - the wanted font
	 */
	public void setFont(Font f){
		this.font = f;
	}
	/**
	 * Sets the fill for the tile
	 * @param fill - the wanted color
	 */
	public void setFill(Color fill){
		this.fill = fill;
	}
	
	/**
	 * Sets the font color for the tile
	 * @param fontColor - the wanted color
	 */
	public void setFontColor(Color fontColor){
		this.fontColor = fontColor;
	}
}
