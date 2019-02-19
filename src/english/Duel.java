package english;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Duel extends BasicGameState {

	private int ID;
	private List<Integer> failures;
	private List<Integer> durations;
	private List<Spell> spells;

	public Duel(int ID) {
		this.ID = ID;
		this.failures = new ArrayList<Integer>();
		this.durations = new ArrayList<Integer>();
	}


	public int getID() {
		return this.ID;
	}

	public void init(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
	}

	public void enter(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
	}

	public void leave(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
	}

	public void keyPressed(int key, char character) {}
	
	public void characterDied(boolean side) {
		//TODO : indiquer la fin du duel
	}
	
	public void launchSpell(int x, int y, boolean side, int star, int damage) {
		Spell spell = new Spell(x, y, side, star, damage);
		spells.add(spell);
	}

}
