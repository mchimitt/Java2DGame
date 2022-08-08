package com.chimitt.game.framework;

import java.awt.image.BufferedImage;

import com.chimitt.game.window.BufferedImageLoader;

public class Texture 
{
	SpriteSheet bs, ps;
	//private BufferedImage block_sheet = null;
	private BufferedImage player_sheet = null;
	
	public BufferedImage[] player = new BufferedImage[6];
	
	public Texture()
	{
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			player_sheet = loader.loadImage("/player_sheet.png");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//bs = new SpriteSheet(block_sheet);
		ps = new SpriteSheet(player_sheet);
		
		getTextures();
	}
	
	public void getTextures()
	{
		player[0] = ps.grabImage(1, 3, 48, 48); //walk right
		player[1] = ps.grabImage(2, 3, 48, 48);
		player[2] = ps.grabImage(3, 3, 48, 48);
		
		player[3] = ps.grabImage(1, 2, 48, 48);	//walk left
		player[4] = ps.grabImage(2, 2, 48, 48);
		player[5] = ps.grabImage(3, 2, 48, 48);
	}
	
	
}
