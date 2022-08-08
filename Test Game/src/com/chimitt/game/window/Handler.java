package com.chimitt.game.window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.chimitt.game.framework.GameObject;
import com.chimitt.game.framework.ObjectId;
import com.chimitt.game.objects.Block;
import com.chimitt.game.objects.Dead;
import com.chimitt.game.objects.Flag;
import com.chimitt.game.objects.Player;

public class Handler 
{
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	private GameObject tempObject;
	
	private Camera cam;
	
	private BufferedImage level0 = null, level1 = null, level2 = null, level3 = null, level4 = null, level5 = null, youwin = null;
	
	public Handler(Camera cam)
	{
		this.cam = cam;
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level0 = loader.loadImage("/level0.png"); //load level0
		level1 = loader.loadImage("/level1.png"); //load level1
		level2 = loader.loadImage("/level2.png"); //load level2
		level3 = loader.loadImage("/level3.png"); //load level3
		level4 = loader.loadImage("/level4.png"); //load level4
		level5 = loader.loadImage("/level5.png"); //load level5
		
		youwin = loader.loadImage("/YouWin.png"); //load gameover screen
	}
	
	
	public void tick()
	{
		for(int i = 0; i < object.size(); i++)
		{
			tempObject = object.get(i);
			
			tempObject.tick(object);
				
		}
	}
	public void render(Graphics g)
	{
		for(int i = 0; i < object.size(); i++)
		{
			tempObject = object.get(i);
			tempObject.render(g);
		}
	}
	
	public void LoadImageLevel(BufferedImage image) 
	{
		int w = image.getWidth();
		int h = image.getHeight();
		
		System.out.println("Width, height " + w + " " + h);
	
		for(int xx = 0; xx<h; xx++)
		{
			for(int yy = 0; yy<w; yy++) 
			{
				int pixel = image.getRGB(xx,yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >>8) & 0xff;
				int blue = (pixel) & 0xff; 
				
				if(red == 255 && green == 255 && blue == 255) 
					addObject(new Block(xx*32, yy*32, ObjectId.Block));
				if(red == 0 && green == 0 && blue == 255)
					addObject(new Player(xx*32, yy*32, this, cam, ObjectId.Player));
				if(red == 255 && green == 0 && blue == 0)
					addObject(new Dead(xx*32, yy*32, ObjectId.Dead));
				if(red == 255 && green == 255 && blue == 0)
					addObject(new Flag(xx*32, yy*32, ObjectId.Flag));
			}
			
		}
		
	}
	
	public void switchLevel() {
		clearLevel();
		cam.setX(0);
		switch(Game.Level)
		{
		case 0:
			LoadImageLevel(level1);
			break;
		case 1:
			LoadImageLevel(level2);
			break;
		case 2:
			LoadImageLevel(level3);
			break;
		case 3:
			LoadImageLevel(level4);
			break;
		case 4:
			LoadImageLevel(level5);
			break;
		case 5:
			LoadImageLevel(youwin);
			break;
		case 6:
			LoadImageLevel(level0);
			Game.setLevel(-1);
			break;
		}
		Game.Level++;
	}
	
	private void clearLevel()
	{
		object.clear();
	}
	
	public void addObject(GameObject object)
	{	
		this.object.add(object);
	}
	
	public void removeObject(GameObject object)
	{
		this.object.remove(object);
	}
	
	
	public void createLevel()
	{
		for(int yy = 0; yy < Game.HEIGHT+32; yy+=32)
			addObject(new Block(0, yy, ObjectId.Block));
		
		for(int xx = 0; xx < Game.WIDTH*2; xx+=32)
			addObject(new Block(xx, Game.HEIGHT-32, ObjectId.Block));
		
		for(int xx = 200; xx < 600; xx += 32)
			addObject(new Block(xx, 400, ObjectId.Block));
		
	}
	
	
}
