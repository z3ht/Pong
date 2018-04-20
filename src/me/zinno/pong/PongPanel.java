package me.zinno.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongPanel extends JPanel implements KeyListener {
	
	private boolean gameIsStarted;
	private final Pong game;
	private JLabel startText;
	
	
	public PongPanel(Pong game) {
		this.game = game;
		this.gameIsStarted = false;
		
		createDefaultSettings();
		createStartText();
	}
	
	private void createDefaultSettings() {
		setBackground(game.getColorScheme().getBackground());
		addKeyListener(this);
		setFocusable(true);
		setLayout(new GridLayout());
	}
	
	private void createStartText() {
		this.startText = new JLabel("Press Any Key to Begin!", SwingConstants.CENTER);
		startText.setFont(new Font("Dialog", Font.BOLD, (int) (game.getScale()*2.5)));
		startText.setForeground(game.getColorScheme().getPrimary());
		this.add(startText);
	}
	
	private void checkForStartGame() {
		if(gameIsStarted)
			return;
		this.gameIsStarted = true;
		this.remove(startText);
		new Thread(game).start();
	}
	
	private void checkForBallKick() {
		if(game.getBall().isBallInMotion())
			return;
		game.getBall().kick();
	}
	
	private Racket racketMoved(int e) {
		for(Racket racket : game.getPlayers()) {
			if(racket.isTriggerKeyPressed(e)) {
				return racket;
			}
		}
		return null;
	}
	
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		Racket racketMoved = racketMoved(e.getKeyCode());
		if(racketMoved == null)
			return;
		racketMoved.setTriggerKeyPressed(e.getKeyCode());
	}
	
	@Override
	public synchronized void keyReleased(KeyEvent e) {
		Racket racketMoved = racketMoved(e.getKeyCode());
		if(racketMoved == null)
			return;
		if(racketMoved.getTriggerKeyPressed() == e.getKeyCode())
			racketMoved.setTriggerKeyPressed(-1);
	}
	
	@Override
	public synchronized void keyTyped(KeyEvent e) {
		checkForStartGame();
		checkForBallKick();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		game.getScoreboard().paint(g); // Must be above all others
		game.getBorder().paint(g);
		for(Racket racket : game.getPlayers())
			racket.paint(g);
		if(gameIsStarted)
			game.getBall().paint(g);
	}
	
	public boolean isGameStarted() {
		return gameIsStarted;
	}
	
	public void setGameIsStarted(boolean gameIsStarted) {
		this.gameIsStarted = gameIsStarted;
	}
}
