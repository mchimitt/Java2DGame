package com.chimitt.game.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.chimitt.game.window.Handler;

public class KeyInput extends KeyAdapter
{
	Handler handler;
	private boolean[] keyDown = new boolean[3];
	
	public KeyInput(Handler handler)
	{
		this.handler = handler;
		
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectId.Player)
			{
				if(key == KeyEvent.VK_D) { tempObject.setVelX(5); keyDown[0] = true; }
				if(key == KeyEvent.VK_A) { tempObject.setVelX(-5); keyDown[1] = true; }
				if(key == KeyEvent.VK_W && !tempObject.isJumping()) 
				{
					tempObject.setVelY(-10);
					tempObject.setJumping(true);
					keyDown[2] = true;
				}
					
			}
		}
		
		if(key==KeyEvent.VK_ESCAPE)
		{
			System.exit(1);
		}
	
	
	}
	
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
			
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectId.Player)
			{
				if(key == KeyEvent.VK_D) keyDown[0] = false; //tempObject.setVelX(0);
				if(key == KeyEvent.VK_A) keyDown[1] = false; //tempObject.setVelX(0);
				
				//horizontal movement
				if(!keyDown[0] && !keyDown[1])
					tempObject.setVelX(0);
				
			}
		}
	}
	
}
