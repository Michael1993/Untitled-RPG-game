package com.joapet99.tile;

import java.util.Collection;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

public class TileMap{
	HashMap<Vector2f, Tile> tiles; //A hash map with all the tiles.
	
	int tilesX; //how many tiles in the x axis per row
	int tilesY; //how many tiles in the y axis per column
	int tileWidth; //standard width for a tile
	int tileHeight; //standard height for a tile
	
	public static Font font; //Default font for every tile
	
	/**
	 * 
	 * @param tilesX - how many columns
	 * @param tilesY - how many rows
	 * @param standardTileWidth - the standard tile width
	 * @param standardTileHeight - the standard tile height
	 */
	public TileMap(int tilesX, int tilesY, int standardTileWidth, int standardTileHeight){
		tiles = new HashMap<Vector2f, Tile>();
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		this.tileWidth = standardTileWidth;
		this.tileHeight = standardTileHeight;
		font = new TrueTypeFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 16), false);
		
		for(int i = 0; i < tilesY;i++){ //For every row...
			for(int j = 0; j < tilesX;j++){ //...for every column...
				tiles.put(new Vector2f(j, i), new Tile(j,i, tileWidth, tileHeight)); //...add a tile
			}
		}
	}
	
	/**
	 * Renders all the tiles
	 * @param gr - needed for drawing and such things
	 */
	public void render(Graphics gr){
		for(Tile t : tiles.values()){
			t.render(gr);
		}
	}
	
	/**
	 * 
	 * @param x - the x position in the tilemap
	 * @param y - the y position in the tilemap
	 * @return
	 * the tile with given x and y coordinate
	 */
	public Tile getTile(int x, int y){
		return tiles.get(new Vector2f(x,y));
	}
	
	/**
	 * 
	 * @param c - the char you want the tile to have
	 * @return
	 * the first occurrence of the given char
	 */
	public Tile getTileByChar(char c){
		for(Tile t : tiles.values()){
			if(t.charInTile != null){
				if(t.charInTile.equals(c)){
					return t;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 * all the tiles
	 */
	public Collection<Tile> getTiles(){
		return tiles.values();
	}
	
	/**
	 * 
	 * @param tilesX - how many columns
	 * @param tileWidth - the width of each tile
	 * @return
	 * the maximum x when given how many columns and how wide the tiles are gonna be
	 */
	public static int getMaxX(int tilesX, int tileWidth){
		return tilesX * tileWidth;
	}
	
	/**
	 * 
	 * @param tilesY - how many rows
	 * @param tileHeight - the height of each tile
	 * @return
	 * the maximum y when given how many rows and how tall the tiles are gonna be
	 */
	public static int getMaxY(int tilesY, int tileHeight){
		return tilesY * tileHeight;
	}
	
	/**
	 * Writes text to the tilemap beginning at the given position
	 * @param startTilePosition - where to start to write
	 * @param text - the text you want to write
	 */
	public void setText(Vector2f startTilePosition, String text){
		char[] characters = text.toCharArray();
		for(int i = 0; i < characters.length;i++){
			int tileX = (int)startTilePosition.x + i;
			int tileY = (int)startTilePosition.y;
			getTile(tileX, tileY).setChar(characters[i]);
		}
	}
	
	/**
	 * Deletes all characters in the row of tiles
	 * @param row - the row you want to clear
	 */
	public void clearLine(int row){
		for(int i = 0; i < tilesX; i++){
			getTile(i,row).charInTile = null;
			getTile(i,row).fill = null;
			getTile(i,row).fontColor = null;
		}
	}
	
	/**
	 * Deletes all characters in the whole tilemap
	 */
	public void clearAll(){
		for(int i = 0; i < tilesY;i++){
			clearLine(i);
		}
	}
	
	/**
	 * Sets the fill color for a range of tiles
	 * @param fontColor - the wanted font color
	 * @param row - the row where the tiles you want are
	 * @param beginIndex - the first tile you want to color
	 * @param endIndex - the last tile you want to color
	 */
	public void setFontColorForTiles(Color fontColor, int row, int beginIndex, int endIndex){
		for(int i = beginIndex; i < endIndex + 1;i++){
			getTile(i, row).setFontColor(fontColor);
		}
	}
	
	/**
	 * Sets the fill color for a rectangle of tiles
	 * @param fontColor - the wanted font color 
	 * @param startPosition - the position the rectangle starts
	 * @param width - the width of the rectangle
	 * @param height - the height of the rectangle
	 */
	public void setFontColorForTiles(Color fontColor, Vector2f startPosition, int width, int height){
		for(int i = (int)startPosition.y; i < startPosition.y + height + 1;i++){
			for(int j = (int) startPosition.x; j < startPosition.x + width + 1;i++){
				getTile(j,i).setFontColor(fontColor);
			}
		}
	}
	
	/**
	 * 
	 * @param fill - the wanted fill color
	 * @param row - the row where the tiles you want are
	 * @param beginIndex - the first tile you want to color
	 * @param endIndex - the last tile you want to color
	 */
	public void setFillForTiles(Color fill, int row, int beginIndex, int endIndex){
		for(int i = beginIndex; i < endIndex + 1;i++){
			getTile(i, row).setFill(fill);
		}
	}
	
	/**
	 * 
	 * @param fill - the wanted fill color
	 * @param startPosition - the position the rectangle starts
	 * @param width - the width of the rectangle
	 * @param height - the height of the rectangle
	 */
	public void setFillForTiles(Color fill, Vector2f startPosition, int width, int height){
		for(int i = (int)startPosition.y; i < startPosition.y + height + 1;i++){
			for(int j = (int) startPosition.x; j < startPosition.x + width + 1;i++){
				getTile(j,i).setFontColor(fill);
			}
		}
	}
	
	/**
	 * 
	 * @return 
	 * how many columns there are in this tilemap
	 */
	public int getTilesX(){
		return tilesX;
	}
	
	/**
	 * 
	 * @return
	 * how many rows there are in this tilemap
	 */
	public int getTilesY(){
		return tilesY;
	}
}
