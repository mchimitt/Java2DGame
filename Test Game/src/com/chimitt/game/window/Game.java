package com.chimitt.game.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.chimitt.game.framework.KeyInput;
import com.chimitt.game.framework.ObjectId;
import com.chimitt.game.framework.Texture;
import com.chimitt.game.objects.Block;
import com.chimitt.game.objects.Dead;
import com.chimitt.game.objects.Flag;
import com.chimitt.game.objects.Player;

public class Game extends Canvas implements Runnable 
{


	private static final long serialVersionUID = 6452612912665976117L;

	public static int Level = 0;
	
	private boolean running = false;
	private Thread thread;
	
	public static int WIDTH, HEIGHT;
	
	public BufferedImage level0 = null, intro = null, jumpIntro = null, deadIntro = null, flagIntro = null, gameOver = null;
	
	
	//Object
	Handler handler;
	Camera cam;
	static Texture tex;
	
	
	Random rand = new Random();
	
	
	private void init()
	{
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		tex = new Texture();
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level0 = loader.loadImage("/level0.png"); //load level0
		intro = loader.loadImage("/Intro.png");
		jumpIntro = loader.loadImage("/JumpIntro.png");
		deadIntro = loader.loadImage("/DeadIntro.png");
		flagIntro = loader.loadImage("/FlagIntro.png");
		gameOver = loader.loadImage("/GameOver.png");
		
		cam = new Camera(0,0);
		handler = new Handler(cam);
		
		
		
		handler.LoadImageLevel(level0);
		
		//handler.addObject(new Player(100, 100, handler, ObjectId.Player));
		//handler.createLevel();
		
		this.addKeyListener(new KeyInput(handler));
	}
	
	
	
	public synchronized void start()
	{
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run()
	{
		init();
		this.requestFocus();
		
		//game loop oh yikes
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	
	public void tick()
	{
		handler.tick();
		for(int i = 0; i<handler.object.size(); i++)
			if(handler.object.get(i).getId() == ObjectId.Player)
				cam.tick(handler.object.get(i));
	}
	
	
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		/////////////////////////////////
		//draw here
		
		
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g2d.translate(cam.getX(), cam.getY());  //begin of cam
		
		if(Level == 0) {
			g.drawImage(intro, -335, 0, null);
			g.drawImage(jumpIntro, 375, 10, null);
			g.drawImage(deadIntro, 1165, 0, null);
			g.drawImage(flagIntro, 1100, 10,null);
		}
		if(Level == 6) {
			g.drawImage(gameOver, 0, 0, null);
		}
		handler.render(g);
		
		
		
		g2d.translate(cam.getX(), cam.getY());  //end of cam
		////////////////////////////////
		
		
		g.dispose();
		bs.show();
		
	}
	
	public static void setLevel(int num)
	{
		Level = num;
	}
	
	public static Texture getInstance() {
		return tex;
	}
	
	public static void main(String agrs[]) 
	{
		new Window(800, 600, "Chimitt's Game", new Game());
	}
	
}
