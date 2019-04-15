package english;

import app.AppLoader;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Entity {

	private String name;
	private int HPmax;
	private int HPcount;
	private int damage;
	private int starMax;
	private int xName;
	private int yName;
	private TextField textField; //TODO : implémenter un TextField basique
	private Duel duel;
	private Exercise exercise;
	private ArrayList<Entity> stars;
	private List<Spell> spells;
	private HealthBar healthBar;
	private float timeAnimRemainingMax;

	/*Etat du character :
	* 0 : iddle
	* 1 : impact
	* 2 : launch spell
	* 3 : death */
	private int state = 0;
	private float timeAnimRemaining;    // Temps avant réinitialisation de l'animation

	public static final Font textFont = AppLoader.loadFont("/fonts/vt323.ttf", java.awt.Font.BOLD, 30);


	public Character(String spritePath, String name, int HPmax, Duel duel, boolean side) {
		super(spritePath,16*5,32*5,32, 32,4, 0, 0, 0, side);
		this.name = name;
		this.HPmax = HPmax;
		this.HPcount = HPmax;
		this.duel = duel;
		this.starMax = 3;
		this.stars = new ArrayList<>();
		this.spells = new ArrayList<>();
		this.healthBar = new HealthBar(side? 1280 : 0, 680,1280/2,40,HPmax,side);
		this.damage = 40;
		this.textField = new TextField(200 + (side ? 640 : 0), 240, 400, 40, 10, 2);
		this.yName = 640;
		this.timeAnimRemainingMax = 500;
		if(!side) {	//TODO : changer les positions des joueurs
			this.setX(80);
			this.setY(440);
			this.xName = 80;
		} else {
			this.setX(1120);
			this.setY(440);
			this.xName = 720;
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);	// Update d'Entity
		for (Spell spell : spells){
			spell.update(container, game, delta);
		}

		if (timeAnimRemaining > 0) {    // gère les fins d'animations
			timeAnimRemaining -= delta;
			if (timeAnimRemaining <= 0){
				if (state == 2){    // Si on est à la fin de l'animation de cast de spell, on lance le spell
					launchSpell();
				}
				state = 0;
				timeAnimRemaining = 0;
			}
		}

	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context, state);	// Render d'Entity
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
		context.drawString(name, xName, yName);

	}

	public void takeDamage(int damageDone) {    // En l'état, prendre des dégats annule le lancer de sort en cours
		HPcount -= damageDone;
		if (HPcount <= 0 ) {
			HPcount = 0;
			timeAnimRemaining = timeAnimRemainingMax;
			state = 3;  // Lance l'animation de mort
			duel.characterDied(side); // Préviens le Duel qu'un joueur est mort
		} else {
			timeAnimRemaining = timeAnimRemainingMax;
			state = 1;  // Lance l'animation des dégats
		}
		healthBar.setCurrentHP(HPcount);

	}

	public void setexercise(Exercise exercise) {
		this.exercise = exercise;
		for (int i = stars.size(); i < starMax; i++){ // On ne remplace que les star qu'il manque
			int posX = getX() - 40 + 60 * i;
			int posY = getY() - 40;
			stars.add(new Star("/images/star.png",40,40, 512, 512, posX,posY,0));
		}
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
		spells.add(new Spell(this.getX(),this.getY(),side,stars.size(), damage));
	}

	public boolean checkAnswer(String text) {
		if(stars.size() > 0 && text.equals(exercise.getAnswer())) {
			return true;
		} else {  // Retire une étoile
			if(stars.size() > 0){
				stars.remove(stars.size()-1);
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
}
