package com.chimitt.game.window;

import com.chimitt.game.framework.GameObject;

public class Camera 
{

	private float x, y;
	
	public Camera(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void tick(GameObject Player)
	{
		x = -Player.getX()+Game.WIDTH/2;
	}
	
	
	
	
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
	
}
