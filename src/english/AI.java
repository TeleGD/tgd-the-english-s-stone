package english;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class AI extends Character {

	private Statistics statistics;
	private boolean frozen;
	private int freezeCountdown;
	private Duel duel;

	public AI(float aspectRatio, String name, int HPmax, Duel duel, boolean side, Statistics statistics) {
		super(aspectRatio, "/images/characters/FierceWizard.png", name, HPmax, duel, side);
		this.statistics = statistics;
		this.frozen = false;
		this.freezeCountdown = 4000;
		this.duel = duel;
		this.showAnswer();
		this.getTextField().setText("???");
		// TODO Auto-generated constructor stub
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		if (this.frozen) {
			if (this.freezeCountdown <= 0) {
				this.freezeCountdown = 4000;
				this.setExercise(this.duel.requestExercise(side));
				this.getTextField().setText("???");
			} else {
				this.freezeCountdown -= delta;
			}
		}
		Input input = container.getInput ();
		if (input.isKeyPressed (Input.KEY_M)) { //TODO : retirer cet Input de debug
			castSpell();
			System.out.println("IA : Je lance un sort !");
		}
	}

	public void chooseGoodAnswer() {
		if (!this.frozen) {
			TextField textField = this.getTextField();
			textField.setText("");
			textField.setCaret(0);
			this.castSpell();
		}
	}

	public void chooseWrongAnswer() {
		if (!this.frozen) {
			String wrongAnswer = "Expecto Patronum";
			TextField textField = this.getTextField();
			this.checkAnswer("Expecto Patronum");
			if (this.getStarCount() == 0) {
				textField.setText("");
				textField.setCaret(0);
				this.frozen = true;
			} else {
				textField.setText(wrongAnswer);
			}
		}
	}

}
