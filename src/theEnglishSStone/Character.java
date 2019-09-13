package theEnglishSStone;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppFont;
import app.AppLoader;

public abstract class Character extends Entity {

	private float aspectRatio;
	private String name;
	private int HPmax;
	protected int HPcount;
	private int damage;
	private int starMax;
	private int xName;
	private int yName;
	private int xQuestion;
	private int yQuestion;
	private int xAnswer;
	private int yAnswer;
	private int xCorrectAnswer;
	private int yCorrectAnswer;
	private TextField textField; //TODO : implémenter un TextField basique
	private Duel duel;
	private Exercise exercise;
	private List<Entity> stars;
	private List<Spell> spells;
	private HealthBar healthBar;
	private float timeAnimRemainingMax;

	/*Etat du character :
	* 0 : iddle
	* 1 : impact
	* 2 : launch spell
	* 3 : death */
	private int state;
	private float timeAnimRemaining;    // Temps avant réinitialisation de l'animation

	private Font textFont;
	private List<Integer> previousFailures;
	private int currentFailure;
	private List<Integer> previousDurations;
	private int currentDuration;
	private boolean isAnswerShown;


	public Character(float aspectRatio, String spritePath, String name, int HPmax, Duel duel, boolean side) {
		super(aspectRatio, spritePath,16*5,32*5,32, 32,4, 0, 0, 0, side);
		this.aspectRatio = aspectRatio;
		this.name = name;
		this.HPmax = HPmax;
		this.HPcount = HPmax;
		this.duel = duel;
		this.starMax = 3;
		this.stars = new ArrayList<>();
		this.spells = new ArrayList<>();
		this.healthBar = new HealthBar(aspectRatio, side? 1280 : 0, 680,1280/2,40,HPmax,side);
		this.damage = 40;
		this.textField = new TextField(aspectRatio, 200 + (side ? 640 : 0), 240, 400, 40, 10, 2);
		this.yName = 640;
		this.yQuestion = 165;
		this.yAnswer = 245;
		this.yCorrectAnswer = 285;
		this.state = 0;
		this.timeAnimRemainingMax = 500;
		this.textFont = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, (int) (30 * aspectRatio));
		int sizeOfName = (int) (textFont.getWidth(name) / aspectRatio);
		this.previousFailures = new ArrayList<Integer>();
		this.currentFailure = 0;
		this.previousDurations = new ArrayList<Integer>();
		this.currentDuration = 0;
		this.isAnswerShown = false;
		if(!side) {	//TODO : changer les positions des joueurs
			this.setX(80);
			this.setY(440);
			this.xName = 80 + 480/2 - sizeOfName/2; // Centrage du nom
			this.xQuestion = 40;
			this.xAnswer = 40;
			this.xCorrectAnswer = 40;
		} else {
			this.setX(1120);
			this.setY(440);
			this.xName = 720 + 480/2 - sizeOfName/2; // Centrage du nom
			this.xQuestion = 680;
			this.xAnswer = 680;
			this.xCorrectAnswer = 680;
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);	// Update d'Entity
		this.currentDuration += delta;
		for (Spell spell : spells){
			spell.update(container, game, delta);
		}

		if (timeAnimRemaining > 0) {    // gère les fins d'animations
			timeAnimRemaining -= delta;
			if (timeAnimRemaining <= 0){
				if (state == 2){    // Si on est à la fin de l'animation de cast de spell, on lance le spell
					launchSpell();
					this.setExercise(this.duel.requestExercise(side));
				} else if (state == 3){
					duel.characterDied(side); // Préviens le Duel qu'un joueur est mort
					game.enterState(2,new FadeOutTransition(), new FadeInTransition());
				}
				state = 0;
				timeAnimRemaining = 0;
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context, state);	// Render d'Entity
		context.setFont(textFont);
		context.setColor(Color.white);
		context.drawString("Question:", this.xQuestion * this.aspectRatio, this.yQuestion * this.aspectRatio);
		String question = this.exercise.getQuestion().replaceAll("^\\s+|\\s+$", "");
		if (!question.isEmpty()) {
			float width = 400 * this.aspectRatio;
			String[] words = question.split("\\s+");
			int length = 1;
			for (int i = 0, j = 1, l = words.length; j < l; j++) {
				String word = words[i] + " " + words[j];
				if (this.textFont.getWidth(word) < width) {
					words[i] = word;
					words[j] = null;
				} else {
					i = j;
					length++;
				}
			}
			int x = (int) ((this.xQuestion + 160) * this.aspectRatio);
			int y = (int) ((this.yQuestion - 20 * (length - 1)) * this.aspectRatio);
			for (int i = 0, j = 0, l = words.length; j < l; j++) {
				if (words[j] != null) {
					context.drawString(words[j], x, y + 40 * i);
					i++;
				}
			}
		}
		context.drawString("Answer:", this.xAnswer * this.aspectRatio, this.yAnswer * this.aspectRatio);
		if (this.isAnswerShown) {
			context.drawString("Expected:", this.xCorrectAnswer * this.aspectRatio, this.yCorrectAnswer * this.aspectRatio);
			context.drawString("\"" + this.exercise.getAnswer() + "\"", (this.xCorrectAnswer + 160) * this.aspectRatio, this.yCorrectAnswer * this.aspectRatio);
		}
		textField.render(container, game, context);
		for (Entity star : stars){  // Render des étoiles
			star.render(container,game,context,0);
		}

		for (Spell spell : spells){
			spell.render(container, game, context);
		}

		healthBar.render(container,game,context);   // Render de la barre de HP
		// Affichage textuel des HP :
		context.setFont(textFont);
		context.setColor(Color.white);
		context.drawString(name, xName * this.aspectRatio, yName * this.aspectRatio);
	}

	public void takeDamage(int damageDone) {    // En l'état, prendre des dégats annule le lancer de sort en cours
		HPcount -= damageDone;
		if (HPcount <= 0 ) {
			HPcount = 0;
			timeAnimRemaining = timeAnimRemainingMax;
			state = 3;  // Lance l'animation de mort
		} else {
			timeAnimRemaining = timeAnimRemainingMax;
			state = 1;  // Lance l'animation des dégats
		}
		healthBar.setCurrentHP(HPcount);

	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
		this.resetFailureAndDuration();
		for (int i = stars.size(); i < starMax; i++){ // On ne remplace que les star qu'il manque
			int posX = getX() - 40 + 60 * i;
			int posY = getY() - 40;
			stars.add(new Star(this.aspectRatio, "/images/star.png",40,40, 512, 512, posX,posY,0));
		}
	}

	public Exercise getExercise() {
		return this.exercise;
	}

	public void resetFailureAndDuration() {
		this.previousFailures.add(this.currentFailure);
		this.currentFailure = 0;
		this.previousDurations.add(this.currentDuration);
		this.currentDuration = 0;
	}

	/**
	 * Lance l'animation de lancer de sort, lancera le sort à la fin de cette animation
	 */
	public void castSpell(){
		if (stars.size() <= 0){
			return;
		}
		timeAnimRemaining = timeAnimRemainingMax;
		state = 2;
	}

	/**
	 * Lance un sort
	 */
	public void launchSpell() {
		spells.add(new Spell(this.aspectRatio, this.getX(),this.getY(),side,stars.size(), damage));
	}

	public boolean checkAnswer(String text) {
		if(stars.size() > 0 && text.equals(exercise.getAnswer())) {
			return true;
		} else {  // Retire une étoile
			if(stars.size() > 0){
				stars.remove(stars.size()-1);
				++this.currentFailure;
			}
			return false;
		}
	}

	public TextField getTextField() {
		return this.textField;
	}

	public void keyPressed(int key, char value) {}

	public List<Spell> getSpells() {
		return spells;
	}

	public void removeSpell(Spell spell){
		spells.remove(spell);
	}

	@Override
	public void loadAnimations(SpriteSheet spriteSheet) {
		loadAnimation(spriteSheet,0,9,0, 0);
		loadAnimation(spriteSheet,0,9,1, 1);
		loadAnimation(spriteSheet,0,9,3, 2);
		loadAnimation(spriteSheet,0,9,4, 3);
		// Maintenant animations contient :
		// Ligne 0 : iddle
		// Ligne 1 : impact
		// Ligne 2 : launch spell
		// Ligne 3 : death
	}

	public void hideAnswer() {
		this.isAnswerShown = false;
	}

	public void showAnswer() {
		this.isAnswerShown = true;
	}

	public boolean isAnswerShown() {
		return this.isAnswerShown;
	}

	public int getStarCount() {
		return this.stars.size();
	}

	public List<Integer> getFailures() {
		return this.previousFailures;
	}

	public List<Integer> getDurations() {
		return this.previousDurations;
	}

}
