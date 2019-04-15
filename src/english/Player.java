package english;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;


public class Player extends Character {

	public Player(String name, int HPmax, Duel duel, boolean side) {
		super("/images/characters/YoungWizard1.png", name, HPmax, duel, side);
		// TODO Auto-generated constructor stub
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		TextField textField = this.getTextField();
		textField.update(container, game, delta);
	}

	public void keyPressed(int key, char value) {
		TextField textField = this.getTextField();
		String text = textField.getText();
		int caret = textField.getCaret();
		switch (key) {
			case Input.KEY_BACK: {
				if (caret > 0) {
					String start = text.substring(0, caret - 1);
					String end = text.substring(caret);
					textField.setText(start + end);
					textField.setCaret(caret - 1);
				}
				return;
			}
			case Input.KEY_ENTER: {
				if (this.checkAnswer(text)) {
					textField.setText("");
					textField.setCaret(0);
					this.launchSpell();
					System.out.println("Je lance un sort !");
				}
				return;
			}
			case Input.KEY_LEFT: {
				if (caret > 0) {
					textField.setCaret(caret - 1);
				}
				return;
			}
			case Input.KEY_RIGHT: {
				int length = text.length();
				if (caret < length) {
					textField.setCaret(caret + 1);
				}
				return;
			}
			case Input.KEY_DELETE: {
				int length = text.length();
				if (caret < length) {
					String start = text.substring(0, caret);
					String end = text.substring(caret + 1);
					textField.setText(start + end);
				}
				return;
			}
		}
		if (value > 0) {
			int length = text.length();
			if (length < 30) { // MAGIC NUMBER
				String start = text.substring(0, caret);
				String end = text.substring(caret);
				textField.setText(start + value + end);
				textField.setCaret(caret + 1);
			}
		}
	}

}
