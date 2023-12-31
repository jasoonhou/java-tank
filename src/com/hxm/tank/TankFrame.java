package com.hxm.tank;

import java.awt.Frame;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TankFrame extends Frame {
	
	Tank myTank = new Tank(200, 200, Dir.DOWN, this);
	List<Bullet> bullets = new ArrayList<>();
	static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
	
	public TankFrame() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setResizable(false);
		setTitle("Tank War");
		setVisible(true);
		
		this.addKeyListener(new MyKeyListener());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	Image offScreenImage = null;
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.white);
		g.drawString("子弹的数量：" + bullets.size(), 10, 60);
		g.setColor(c);
		
		myTank.paint(g);
		for(int i=0; i<bullets.size(); i++) {
			bullets.get(i).paint(g);
		}
		
//		for(Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
//			Bullet b = it.next();
//			if(!b.live) it.remove();
//		}
	}
	
	class MyKeyListener extends KeyAdapter {
		
		boolean bl = false;
		boolean br = false;
		boolean bu = false;
		boolean bd = false;
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
				case KeyEvent.VK_LEFT:
					bl = true;
					break;
				case KeyEvent.VK_RIGHT:
					br = true;
					break;
				case KeyEvent.VK_UP:
					bu = true;
					break;
				case KeyEvent.VK_DOWN:
					bd = true;
					break;
					
				default:
					break;
			}
			

			setMainTankDir();
		}
		
		@Override
		public void keyReleased(KeyEvent e) {

			int key = e.getKeyCode();
			switch (key) {
				case KeyEvent.VK_LEFT:
					bl = false;
					break;
				case KeyEvent.VK_RIGHT:
					br = false;
					break;
				case KeyEvent.VK_UP:
					bu = false;
					break;
				case KeyEvent.VK_DOWN:
					bd = false;
					break;
				case KeyEvent.VK_CONTROL:
					myTank.fire();
					break;
				default:
					break;
			}
			
			setMainTankDir();
		}
		
		private void setMainTankDir() {
			if(!bl && !br && !bu && !bd) myTank.setMoving(false);
			
			else {
				myTank.setMoving(true);
				
				if(bl) myTank.setDir(Dir.LEFT);
				if(br) myTank.setDir(Dir.RIGHT);
				if(bu) myTank.setDir(Dir.UP);
				if(bd) myTank.setDir(Dir.DOWN);
			}
		}
	}
}
