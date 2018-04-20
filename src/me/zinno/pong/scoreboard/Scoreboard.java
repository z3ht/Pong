package me.zinno.pong.scoreboard;

import me.zinno.pong.Pong;
import me.zinno.pong.Racket;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Scoreboard {
	
	private List<Scorable> competitors = new LinkedList<>();
	private Pong game;
	private int fontSize;
	
	public Scoreboard(Pong game, int fontSize) {
		this.game = game;
		this.fontSize = fontSize;
	}
	
	public void paint(Graphics g) {
		g.setColor(game.getColorScheme().getPrimary());
		g.setFont(new Font("DialogInput", Font.BOLD, fontSize));
		int lines = 0;
		for(String text : createScoreText(g.getFontMetrics())) {
			int textWidth = g.getFontMetrics().stringWidth(text);
			int textHeight = g.getFontMetrics().getHeight();
			g.drawString(text, (game.getWidth()/2) - (textWidth/2), ((lines+1)*textHeight));
			lines++;
		}
	}
	
	private String[] createScoreText(FontMetrics metrics) {
		StringBuilder names = new StringBuilder();
		StringBuilder scores = new StringBuilder();
		
		for(Scorable competitor : competitors) {
			String name = (names.toString().length() != 0) ? " || " + competitor.getScoreboardName() : competitor.getScoreboardName();
			StringBuilder score = new StringBuilder();
			while(metrics.stringWidth(score.toString()) < (metrics.stringWidth(name)/2)) {
				score.append(" ");
			}
			score.append(competitor.getScore());
			while(metrics.stringWidth(score.toString()) < (metrics.stringWidth(name))) {
				score.append(" ");
			}
			names.append(name);
			scores.append(score);
		}
		return new String[] {names.toString(), scores.toString()};
	}
	
	public void addCompetitor(Racket player) {
		competitors.add(player);
	}
	
	public int getHeight() {
		return 3*fontSize;
	}
}
