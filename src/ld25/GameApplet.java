package ld25;
import java.applet.Applet;
import java.awt.BorderLayout;

public class GameApplet extends Applet {
	private static final long serialVersionUID = 1L;
	
	private Game game = new Game();

	@Override
	public void init() {
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
	}
	
	@Override
	public void start() {
		game.start();
	}
	
	@Override
	public void stop() {
		game.stop();
	}
}
