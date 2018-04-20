package me.zinno.pong;

import me.zinno.pong.colors.ColorScheme;
import me.zinno.pong.scoreboard.Scoreboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

public class Pong extends JFrame implements Runnable {
	
	private final int SCREEN_WIDTH, SCREEN_HEIGHT;
	private final int scale;
	private ColorScheme colorScheme;
	private PongPanel panel;
	private List<Racket> players = new LinkedList<>();
	private Ball ball;
	private Scoreboard scoreboard;
	private Border border;
	
	public Pong(ColorScheme colorScheme, int screenWidth, int screenHeight) {
		this.colorScheme = colorScheme;
		this.SCREEN_WIDTH = Math.max(200, screenWidth);
		screenHeight = Math.max(160, screenHeight);
		this.SCREEN_HEIGHT = Math.min((SCREEN_WIDTH * 4)/5, screenHeight);
		
		createDefaultSettings();
		
		this.scale = Math.min(this.getWidth() / 70, this.getHeight() / 35);
		this.panel = new PongPanel(this);
		this.scoreboard = new Scoreboard(this);
		this.border = new Border(this);
		this.ball = new Ball(this,
				new Point((border.getxMax()-border.getxMin())/2 + border.getxMin(), (border.getyMax()-border.getyMin())/2 + border.getyMin()));
		createPlayers();
		for(Racket player : players)
			scoreboard.addCompetitor(player);
		
		add(this.panel);
		setVisible(true);
	}
	
	private void createPlayers() {
		int yDiameter = border.getyMax() - border.getyMin();
		int xDiameter = border.getxMax() - border.getxMin();
		
		this.players.add(new Racket("Pinger", new int[] {KeyEvent.VK_W, KeyEvent.VK_A}, new int[] {KeyEvent.VK_S, KeyEvent.VK_D},
				new Point((int) (xDiameter*.02) + border.getxMin() - (int) (xDiameter*.014), (yDiameter/2) - (int) (yDiameter*.0625) + border.getyMin()),
				this));
		this.players.add(new Racket("Ponger", new int[] {KeyEvent.VK_UP, KeyEvent.VK_LEFT}, new int[] {KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT},
				new Point((int) (xDiameter*.98) + border.getxMin(), (yDiameter/2) - (int) (yDiameter*.0625) + border.getyMin()),
				this));
	}
	
	private void createDefaultSettings() {
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Pong");
		setLayout(new GridLayout());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	@Override
	public void run() {
		while(panel.isGameStarted()) {
			try {
				for(Racket racket : getPlayers())
					racket.update();
				ball.update();
				panel.repaint();
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ColorScheme getColorScheme() {
		return colorScheme;
	}
	
	public PongPanel getPanel() {
		return panel;
	}
	
	public int getScale() {
		return scale;
	}
	
	public List<Racket> getPlayers() {
		return players;
	}
	
	public Ball getBall() {
		return ball;
	}
	
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	
	public Border getBorder() {
		return border;
	}
	
	public static void main(String[] args) {
		Pong game = new Pong(new ColorScheme(Color.WHITE, Color.RED, Color.BLACK), 1000, 500);
	}
}
