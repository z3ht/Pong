package me.zinno.pong.colors;

import java.awt.*;

public class ColorScheme {
	
	private final Color primary;
	private final Color secondary;
	private final Color background;
	
	public ColorScheme(Color primary, Color secondary, Color background) {
		this.primary = primary;
		this.secondary = secondary;
		this.background = background;
	}
	
	public Color getPrimary() {
		return primary;
	}
	
	public Color getSecondary() {
		return secondary;
	}
	
	public Color getBackground() {
		return background;
	}
}
