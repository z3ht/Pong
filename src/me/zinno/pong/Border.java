package me.zinno.pong;

import java.awt.*;

public class Border {
	
	private final double PERCENT_PADDING = 0.01;
	private final Pong game;
	
	private final int xMin;
	private final int xMax;
	private final int yMin;
	private final int yMax;
	
	public Border(Pong game) {
		this.game = game;
		
		this.xMin = (int) (game.getWidth()*PERCENT_PADDING - 3);
		this.xMax = (int) (game.getWidth()*(1-PERCENT_PADDING) - 3);
		this.yMin = (int) (game.getHeight()*(1-PERCENT_PADDING)  - 35);
		this.yMax = (int) ((game.getHeight()*PERCENT_PADDING) + game.getScoreboard().getHeight());
	}
	
	public void paint(Graphics g) {
		
		g.setColor(game.getColorScheme().getPrimary());
		
		g.drawLine(xMin, yMin, xMin, yMax);
		g.drawLine(xMax, yMin, xMax, yMax);
		g.drawLine(xMin, yMax, xMax, yMax);
		g.drawLine(xMin, yMin, xMax, yMin);
	}
	
	public int getxMin() {
		return xMin;
	}
	
	public int getxMax() {
		return xMax;
	}
	
	public int getyMin() {
		return yMax;
	}
	
	public int getyMax() {
		return yMin;
	}
	
}
