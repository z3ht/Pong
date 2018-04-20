package me.zinno.pong;

import java.awt.*;

public class Ball {
	
	private Pong game;
	private Racket lastToTouch;
	private int xPos;
	private int yPos;
	private Point startPos;
	private int xVel;
	private int yVel;
	private int maxAccel;
	private int length;
	private boolean ballInMotion;
	
	public Ball(Pong game, Point startPos) {
		this.game = game;
		this.xPos = (int)  startPos.getX();
		this.yPos = (int) startPos.getY();
		this.length = (int) ((game.getBorder().getxMax()-game.getBorder().getxMin())*.014);
		this.maxAccel = (int) (game.getScale()*.3);
		this.startPos = startPos;
		this.lastToTouch = null;
		this.ballInMotion = false;
	}
	
	public void paint(Graphics g) {
		g.fillRect(xPos, yPos, length, length);
	}
	
	public void update() {
		checkCollisionOnRackets();
		checkCollisionOnSouthToNorthSides();
		checkCollisionOnEastToWestSides();
		
		if(ballInMotion) {
			xPos += xVel;
			yPos += yVel;
		}
	}
	
	private void checkCollisionOnRackets() {
		for(Racket racket : game.getPlayers()) {
			int xDist = Math.abs(racket.getxPos() - xPos);
			int yDist = Math.abs(racket.getyPos() - yPos);
			int xBubble = (racket.getxSize() + length)/2;
			int yBubble = (racket.getySize() + length)/2;
			
			if(xDist > xBubble || yDist > yBubble)
				continue;
			
			if(lastToTouch == racket)
				return;
			
			this.lastToTouch = racket;
			
			kick();
			xVel *= -1;
			
			return;
		}
	}
	
	private void checkCollisionOnSouthToNorthSides() {
		if(xPos < game.getBorder().getxMax() &&
				xPos > game.getBorder().getxMin())
			return;
		
		if(lastToTouch != null)
			lastToTouch.setScore(lastToTouch.getScore() + 1);
		resetBall();
		kick();
		this.ballInMotion = false;
	}
	
	private void resetBall() {
		this.xPos = startPos.x;
		this.yPos = startPos.y;
		lastToTouch = null;
	}
	
	private void checkCollisionOnEastToWestSides() {
		if(yPos < game.getBorder().getyMax() - length && yPos > game.getBorder().getyMin())
			return;
		
		yVel *= -1;
	}
	
	public void kick() {
		this.ballInMotion = true;
		xVel = (xVel > 0) ? (int) (((Math.random()*.8)+.25)* maxAccel) : (int) (((Math.random()*.8)+.25)* maxAccel)*-1;
		yVel = (yVel > 0) ? (int) (((Math.random()*.8)+.25)* maxAccel) : (int) (((Math.random()*.8)+.25)* maxAccel)*-1;
	}
	
	public boolean isBallInMotion() {
		return ballInMotion;
	}
	
	public void setBallInMotion(boolean ballInMotion) {
		this.ballInMotion = ballInMotion;
	}
}
