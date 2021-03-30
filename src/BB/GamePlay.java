package BB;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{

	private boolean play = false;
	private int score=0;
	private int totalBrics= 21;
	private Timer timer;
	private int delay=8;
	private int playerX = 310;
	private int ballposX= 10;
	private int ballposY= 20;
	private int ballYdir= -1;
	private int ballXdir= -2;
	private MapGenerator map;
	public GamePlay() {
		
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(1,1,870,700);
		
		map.draw((Graphics2D )g);
		g.setColor(Color.yellow);
		g.fillRect(0,0,3,700);
		
		g.fillRect(0,0 ,870,3);
		g.fillRect(870,1,870,700);
	
		g.setColor(Color.blue);
		g.fillRect(playerX,650,100,8);
		g.setColor(Color.green);
		g.fillOval(ballposX,ballposY,20,20);
		
		g.setColor(Color.black);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+ score,590,300);
		
		if(totalBrics<=0) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won ,Score"+ score,190,300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Please Enter to Restart",230,350);
			
		}
		
		
		if(ballposY >700) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over,Score"+ score,190,300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Please Enter to Restart",230,350);
		}
		g.dispose();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 650, 100, 8))) {
				ballYdir = - ballYdir;
			}
		for(int i=0; i< map.map.length; i++) {
		 for(int j=0 ; j<map.map[0].length; j++) {
				if(map.map[i][j] > 0) {
					int brickX= j*map.brickWidth +80;
					int brickY= i*map.brickHeight +50;
					int brickWidth=map.brickWidth;
					int brickHeight=map.brickHeight;
					Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight); 
				    Rectangle ball=new Rectangle(ballposX, ballposY,20,20);
					Rectangle rectBrick=rect;
							
							if(ball.intersects(rectBrick)) {
								map.setBrickValue(0,i,j);
							totalBrics--;
					        score+=5;
								if((ballposX +20 <= rectBrick.x) ||( ballposX +20 >= rectBrick.x + rectBrick.width)) {
									ballXdir = -ballXdir;
								}
								else {
									ballYdir  =-ballYdir;
								}
							}
						
					}
				
				
			}
			}
	
		
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX<0) {
				ballXdir =-ballXdir;
			}
			//BOUNCING EFFECT
			if(ballposY<0) {
				ballYdir =-ballYdir;
			}
			if(ballposX>830) {
				ballXdir =-ballXdir;
			}
		}
		
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >=770) {
				playerX=770;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
					
			}
		
	     }
		if(e.getKeyCode()== KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				ballposX= 120;
				ballposY=350;
				ballXdir=-1;
				ballYdir=-2;
				score=0;
				totalBrics=21;
				map=new MapGenerator(3,7);
				repaint();
			}
		}
		}
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
