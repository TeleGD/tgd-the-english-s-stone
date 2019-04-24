package english;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class Player extends Character {

	private float aspectRatio;
	private Font countDownFont;
	private Color countDownColor;
	private int freezeCountdown;
	private Duel duel;

	public Player(float aspectRatio, String name, int HPmax, Duel duel, boolean side) {
		super(aspectRatio, "/images/characters/YoungWizard.png", name, HPmax, duel, side);
		this.aspectRatio = aspectRatio;
		this.countDownFont = AppLoader.loadFont("/fonts/vt323.ttf", java.awt.Font.PLAIN, (int) (30 * aspectRatio));
		this.countDownColor = Color.white;
		this.freezeCountdown = 4000;
		this.duel = duel;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		TextField textField = this.getTextField();
		if (this.isAnswerShown()) {
			if (this.freezeCountdown <= 0) {
				this.freezeCountdown = 4000;
				this.hideAnswer();
				this.setExercise(this.duel.requestExercise(side));
			} else {
				this.freezeCountdown -= delta;
			}
		} else {
			textField.update(container, game, delta);
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
		if (this.isAnswerShown()) {
			context.setFont(this.countDownFont);
			context.setColor(this.countDownColor);
			String countDown = "" + (this.freezeCountdown / 1000 + 1);
			if (this.getSide()) {
				context.drawString(countDown, (int) ((1160 - (int) (this.countDownFont.getWidth(countDown) / this.aspectRatio) / 2) * this.aspectRatio), 405);
			} else {
				context.drawString(countDown, (int) ((120 - (int) (this.countDownFont.getWidth(countDown) / this.aspectRatio) / 2) * this.aspectRatio), 405);
			}
		}
	}

	public void keyPressed(int key, char value) {
		if (this.isAnswerShown()) {
			return;
		}
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
					this.castSpell();
					System.out.println("Joueur : Je lance un sort !");
				} else if (this.getStarCount() == 0) {
					textField.setText("");
					textField.setCaret(0);
					this.showAnswer();
				}
				return;
			}
			case Input.KEY_RCONTROL: {  // TODO : retirer ce débug plus tard
				this.castSpell();
				System.out.println("Joueur : JE CHEAT : Je lance un sort !");
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
		if (value >= 32) {
			int length = text.length();
			if (length < 30) { // MAGIC NUMBER
				String start = text.substring(0, caret);
				String end = text.substring(caret);
				textField.setText(start + value + end);
				textField.setCaret(caret + 1);
			}
		}
	}

	public Statistics getStatistics() {
		return new Statistics(this.getFailures(), this.getDurations());
	}

}
