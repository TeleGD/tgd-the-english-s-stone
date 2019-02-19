package english;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import app.ui.TextField;

public abstract class Character extends Entity {

	private String name;
	private int HPmax;
	private int HPcount;
	private int damage;
	private int starMax;
	private int starCount;
	private TextField textField; //TODO : implémenter un TextField basique
	private Duel duel;
	private Exercise exercise;

	public Character(String spritePath, String name, int HPmax, Duel duel, boolean side) {
		super(spritePath, 0, 0, 0);
		this.name = name;
		this.HPmax = HPmax;
		this.duel = duel;
		this.side = side;
		
		if(side) {	//TODO : changer les positions des joueurs
			this.setX(10);
			this.setY(400);
		} else {
			this.setX(400);
			this.setY(400);
		}
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);	// Update d'Entity
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);	// Render d'Entity
	}
	
	public void takeDamage(int damageDone) {
		HPcount -= damageDone;
		if (HPcount <= 0 ) {
			HPcount = 0;
			duel.characterDied(side); // Préviens le Duel qu'un joueur est mort
		}
	}
	
	public void setexercise(Exercise exercise) {
		this.exercise = exercise;
		starCount = starMax;
	}
	
	public void launchSpell() {
		duel.launchSpell(this.getX(),this.getY(),side,starCount, damage);  // Faire partir le sort de la main du Character, plutôt que depuis sa position
	}
	
	public boolean checkAnswer() {
		if(starCount > 0 && textField.getText().equals(exercise.getAnswer())) {
			launchSpell();
			return true;
		} else {  // Retire une étoile
			starCount--;
			if(starCount <= 0) {
				starCount = 0;
			}
			return false;
		}
	}

}
