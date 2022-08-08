package com.chimitt.game.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.chimitt.game.framework.GameObject;
import com.chimitt.game.framework.ObjectId;
import com.chimitt.game.framework.Texture;
import com.chimitt.game.window.Animation;
import com.chimitt.game.window.Camera;
import com.chimitt.game.window.Game;
import com.chimitt.game.window.Handler;

public class Player extends GameObject
{

	private float width = 64, height = 64;
	
	private int spawnX, spawnY;
	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;
	private int facing = 1;
	//if facing = 1, facing right
	//if facing = -1, facing left
	
	private Handler handler;
	private Camera cam;
	
	
	Texture tex = Game.getInstance();
	
	private Animation walkRight;
	private Animation walkLeft;
	int aniSpeed = 5;
	
	
	
	
	public Player(float x, float y, Handler handler, Camera cam, ObjectId id) 
	{
		super(x, y, id);
		this.handler = handler;
		this.cam = cam;
		
		walkRight = new Animation(aniSpeed, tex.player[0], tex.player[1], tex.player[2]);
		walkLeft = new Animation(aniSpeed, tex.player[3], tex.player[4], tex.player[5]);
		spawnX = (int)x;
		spawnY = (int)y;
	}

	public void tick(LinkedList<GameObject> object) 
	{
		x+=velX;
		y+=velY;
		
		if(velX < 0) facing = -1;
		if(velX > 0) facing = 1;
		
		if(falling||jumping)
		{
			velY+=gravity;
			
			if(velY > MAX_SPEED)
				velY = MAX_SPEED;
		}
		Collision(object);
		
		walkRight.runAnimation();
		walkLeft.runAnimation();
	}
	
	private void Collision(LinkedList<GameObject> object)
	{
		for(int i = 0; i<handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Block)
			{
				if(getBoundsTop().intersects(tempObject.getBounds()))
				{
					y=tempObject.getY()+32;
					velY=0;
				}
				
				if(getBounds().intersects(tempObject.getBounds()))
				{
					y=tempObject.getY() - height;
					velY=0;
					falling=false;
					jumping=false;
				}
				else
				{
					falling = true;
				}
				
				//Right
				if(getBoundsRight().intersects(tempObject.getBounds()))
				{
					x = tempObject.getX() - width;
				}
				
				//Left
				if(getBoundsLeft().intersects(tempObject.getBounds()))
				{
					x = tempObject.getX() + 32;
				}
				
			}
			else if(tempObject.getId() == ObjectId.Dead)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					x = spawnX;
					y = spawnY;
				}
			}
			else if(tempObject.getId() == ObjectId.Flag)
			{
				//switch level
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.switchLevel();
				}
				
			}
		}
	}

	public void render(Graphics g) 
	{
		g.setColor(Color.blue);
		if(jumping)
		{
			if(facing == 1)
			{
				g.drawImage(tex.player[0], (int)x, (int)y, (int)width, (int)height, null);
			}
			else if(facing == -1)
			{
				g.drawImage(tex.player[3], (int)x, (int)y, (int)width, (int)height, null);
			}
		}
		else {
			if(velX!=0){
				if(facing == 1)
					walkRight.drawAnimation(g, (int)x, (int)y, (int)width, (int)height);
				else if(facing == -1)
					walkLeft.drawAnimation(g, (int)x, (int)y, (int)width, (int)height);
			}
			else {
				if(facing == 1)
					g.drawImage(tex.player[1], (int)x, (int)y, (int)width, (int)height, null);
				if(facing == -1)
					g.drawImage(tex.player[4], (int)x, (int)y, (int)width, (int)height, null);
			}
		}
		
		/*
		debugging for the collision detection:
		
			Graphics2D g2d = (Graphics2D) g;
			g.setColor(Color.red);
		
			g2d.draw(getBounds());
			g2d.draw(getBoundsRight());
			g2d.draw(getBoundsLeft());
			g2d.draw(getBoundsTop());
		 */
	}
	

	public Rectangle getBounds() 
	{
		return new Rectangle((int) ((int)x+(width/4)), (int) ((int)y+(height/2)), (int)width/2, (int)height/2);
	}
	public Rectangle getBoundsTop() 
	{
		return new Rectangle((int) ((int)x+(width/4)), (int)y, (int)width/2, (int)height/2);
	}
	public Rectangle getBoundsRight() 
	{
		return new Rectangle((int) ((int)x+(width-5)), (int)y+5, (int)5, (int)height-10);
	}
	public Rectangle getBoundsLeft() 
	{
		return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
	}
	
}
