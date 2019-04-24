package english;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class AI extends Character {

	static private String[] wrongAnswers;

	static {
		AI.wrongAnswers = new String[]{
			"Accio!",
			"Alohomora!",
			"Expelliarmus!",
			"Expecto Patronum!",
			"Lumos!",
			"Nox!",
			"Obliviate!",
			"Wingardium Leviosa!"
		};
	}

	private Statistics statistics;
	private float aspectRatio;
	private Font countDownFont;
	private Color countDownColor;
	private boolean frozen;
	private int freezeCountdown;
	private Duel duel;

	public AI(float aspectRatio, String name, int HPmax, Duel duel, boolean side, Statistics statistics) {
		super(aspectRatio, "/images/characters/FierceWizard.png", name, HPmax, duel, side);
		this.statistics = statistics;
		this.aspectRatio = aspectRatio;
		this.countDownFont = AppLoader.loadFont("/fonts/vt323.ttf", java.awt.Font.PLAIN, (int) (30 * aspectRatio));
		this.countDownColor = Color.white;
		this.frozen = false;
		this.freezeCountdown = 4000;
		this.duel = duel;
		this.showAnswer();
		this.getTextField().setText("???");
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		if (this.frozen) {
			if (this.freezeCountdown <= 0) {
				this.frozen = false;
				this.freezeCountdown = 4000;
				this.setExercise(this.duel.requestExercise(side));
				this.getTextField().setText("???");
			} else {
				this.freezeCountdown -= delta;
			}
		}
		Input input = container.getInput ();
		if (input.isKeyPressed (Input.KEY_M)) { //TODO : retirer cet Input de debug
			this.chooseGoodAnswer();
			System.out.println("IA : Je lance un sort !");
		} else if (input.isKeyPressed (Input.KEY_P)) { //TODO : retirer cet Input de debug
			this.chooseWrongAnswer();
			System.out.println("IA : Je lance un sort !");
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
		if (this.frozen) {
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

	public void chooseGoodAnswer() {
		if (!this.frozen) {
			TextField textField = this.getTextField();
			textField.setText("???");
			this.castSpell();
		}
	}

	public void chooseWrongAnswer() {
		if (!this.frozen) {
			String wrongAnswer = AI.wrongAnswers[this.duel.getRNG().nextInt(AI.wrongAnswers.length)];
			TextField textField = this.getTextField();
			this.checkAnswer(wrongAnswer);
			if (this.getStarCount() == 0) {
				textField.setText("");
				this.frozen = true;
			} else {
				textField.setText(wrongAnswer);
			}
		}
	}

}
