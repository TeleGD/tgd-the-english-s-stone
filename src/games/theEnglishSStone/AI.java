package games.theEnglishSStone;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import app.AppFont;
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

	private int nbWrongAnswers;
	private int timeBeforeNextAnswer;
	private int minTimeBeforeNextAnswer;
	private int maxTimeBeforeNextAnswer;
	private int answerCountdown;

	public AI(float aspectRatio, String name, int HPmax, Duel duel, boolean side, Statistics statistics) {
		super(aspectRatio, "/images/theEnglishSStone/characters/FierceWizard.png", name, HPmax, duel, side);
		this.statistics = statistics;
		this.aspectRatio = aspectRatio;
		this.countDownFont = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.PLAIN, (int) (30 * aspectRatio));
		this.countDownColor = Color.white;
		this.frozen = false;
		this.freezeCountdown = 4000;
		this.duel = duel;
		this.showAnswer();
		this.getTextField().setText("???");

		this.minTimeBeforeNextAnswer = 3000;
		this.maxTimeBeforeNextAnswer = 10000;
		computeAnswerStats();
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
		}else {
			this.answerCountdown -= delta;
			if (answerCountdown <= 0){
				if (nbWrongAnswers <= 0){   // on n'a plus de mauvaise réponse à donner : on donne la bonne
					this.chooseGoodAnswer();
				} else{
					this.chooseWrongAnswer();
					answerCountdown = timeBeforeNextAnswer; // Relance le countdown
				}
			}
		}

		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_M)) { //TODO : retirer cet Input de debug
			this.chooseGoodAnswer();
			System.out.println("IA : Je lance un sort !");
		} else if (input.isKeyPressed(Input.KEY_P)) { //TODO : retirer cet Input de debug
			this.chooseWrongAnswer();
			System.out.println("IA : Je lance un sort !");
		}
	}

	public void computeAnswerStats(){
		this.nbWrongAnswers = (int) (statistics.getFailureMean() + duel.getRNG().nextGaussian() * statistics.getFailureDeviation());
		nbWrongAnswers = Math.max(nbWrongAnswers, 0);
		nbWrongAnswers = Math.min(nbWrongAnswers, 3);


		this.timeBeforeNextAnswer = (int) ((statistics.getDurationMean() + duel.getRNG().nextGaussian() * statistics.getDurationDeviation()) *  1.30); // L'IA laisse une marge de 30% au joueur
		timeBeforeNextAnswer = Math.max(timeBeforeNextAnswer, minTimeBeforeNextAnswer);
		timeBeforeNextAnswer = (int) Math.min(timeBeforeNextAnswer, maxTimeBeforeNextAnswer);
		if (nbWrongAnswers > 0) {
			timeBeforeNextAnswer /= nbWrongAnswers;
		}

		answerCountdown = timeBeforeNextAnswer;
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
		if (!this.frozen && this.HPcount > 0) {
			TextField textField = this.getTextField();
			textField.setText("???");
			this.castSpell();
			computeAnswerStats();
		}
	}

	public void chooseWrongAnswer() {
		if (!this.frozen && this.HPcount > 0) {
			String wrongAnswer = AI.wrongAnswers[this.duel.getRNG().nextInt(AI.wrongAnswers.length)];
			TextField textField = this.getTextField();
			this.checkAnswer(wrongAnswer);
			if (this.getStarCount() == 0) {
				textField.setText("");
				this.frozen = true;
			} else {
				textField.setText(wrongAnswer);
			}
			nbWrongAnswers --;
		}
	}

}
