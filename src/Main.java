import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.util.List;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import english.Loader;
import english.Subject;

public final class Main {

	public static final void main(String[] arguments) throws SlickException {
		String title = "The English's stone";
		int width = 1280;
		int height = 720;
		boolean fullscreen = false;
		String request = "Do you want to play in fullscreen?";
		Object[] options = {
			"Yes",
			"No"
		};
		int returnValue = JOptionPane.showOptionDialog(
			null,
			request,
			title,
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]
		);
		if (returnValue != -1) {
			if (returnValue == 0) {
				DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
				width = display.getWidth();
				height = display.getHeight();
				fullscreen = true;
			}
			List<Subject> subjects = Loader.restore();
			StateBasedGame game = new StateBasedGame(title) {

				public void initStatesList(GameContainer container) {
					this.addState(new pages.Welcome(0));
					this.addState(new english.Duel(1));
					this.addState(new pages.Choice(2, this, subjects));
				}

			};
			AppGameContainer container = new AppGameContainer(game, width, height, fullscreen) {

				public void destroy() {
					Loader.save(subjects);
				}

				public void exit() {
					Loader.save(subjects);
				}

			};
			container.setTargetFrameRate(60);
			container.setVSync(true);
			container.setShowFPS(false);
			container.start();
		}
	}

}
