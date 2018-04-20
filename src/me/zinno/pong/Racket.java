package me.zinno.pong;

import me.zinno.pong.scoreboard.Scorable;

import javax.swing.*;
import java.awt.*;

public class Racket extends JPanel implements Scorable {
	
	private String name;
	private int score;
	private int[] upTriggerKeys;
	private int[] downTriggerKeys;
	private int triggerKeyPressed;
	private Pong game;
	
	private int speed;
	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;
	
	public Racket(String name, int[] upTriggerKeys, int[] downTriggerKeys, Point startingPos, Point size, int speed, Pong game) {
		this.upTriggerKeys = upTriggerKeys;
		this.downTriggerKeys = downTriggerKeys;
		this.game = game;
		this.name = name;
		this.score = 0;
		this.speed = speed;
		this.xPos = (int) startingPos.getX();
		this.yPos = (int) startingPos.getY();
		this.xSize = (int) size.getX();
		this.ySize = (int) size.getY();
	}
	
	public boolean isTriggerKeyPressed(int pressedKey) {
		for(int key : downTriggerKeys) {
			if(pressedKey == key)
				return true;
		}
		for(int key : upTriggerKeys) {
			if(pressedKey == key) {
				return true;
			}
		}
		return false;
	}
	
	public synchronized void update() {
		for(int triggerKey : downTriggerKeys)
			if(getTriggerKeyPressed() == triggerKey && yPos + speed < game.getBorder().getyMax() - ySize)
				yPos += speed;
		for(int triggerKey : upTriggerKeys)
			if(getTriggerKeyPressed() == triggerKey && yPos - speed > game.getBorder().getyMin())
				yPos -= speed;
	}
	
	public void paint(Graphics g) {
		g.fillRect(xPos, yPos, xSize, ySize);
	}
	
	@Override
	public String getScoreboardName() {
		return this.name;
	}
	
	@Override
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public synchronized int getTriggerKeyPressed() {
		return triggerKeyPressed;
	}
	
	public synchronized void setTriggerKeyPressed(int triggerKeyPressed) {
		this.triggerKeyPressed = triggerKeyPressed;
	}
	
	public int getxPos() {
		return xPos;
	}
	
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	
	public int getyPos() {
		return yPos;
	}
	
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	public int getxSize() {
		return xSize;
	}
	
	public void setxSize(int xSize) {
		this.xSize = xSize;
	}
	
	public int getySize() {
		return ySize;
	}
	
	public void setySize(int ySize) {
		this.ySize = ySize;
	}
}
