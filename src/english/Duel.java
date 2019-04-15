package english;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

public class Duel extends BasicGameState {

	static private Random RNG;
	static private Font font;

	static {
		Duel.RNG = new Random();
		Duel.font = AppLoader.loadFont("/fonts/press-start-2p.ttf", java.awt.Font.BOLD, 40);
	}

	private int ID;
	private Subject subject;
	private Chapter chapter;
	private Character[] characters;
	private List<Integer> failures;
	private List<Integer> durations;
	private List<Spell> spells;
	private String title;
	private String subTitle;
	private Font titleFont;
	private Color titleColor;
	private int titleX;
	private int titleY;
	private Font subTitleFont;
	private Color subTitleColor;
	private int subTitleX;
	private int subTitleY;

	public Duel(int ID) {
		this.ID = ID;
		this.characters = new Character[2];
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

		// Updates des Entity :
		for (Character character: this.characters) {
			character.update(container, game, delta);
		}
		for (Spell spell : this.spells) {
			spell.update(container, game, delta);
		}

		//Collisions :
		if (spells.size()==2) {	// Collision entre Spell
			Spell spell0 = spells.get(0);
			Spell spell1 = spells.get(1);
			if (spell0.getX() + spell0.getSpriteWidth() > spell1.getX()) {	// S'il y a collision entre Spell
				//Collision entre Spell
				int starOfSpell0 =spell0.getStar();
				spell0.collideWithOtherSpell(spell1.getStar());
				spell1.collideWithOtherSpell(starOfSpell0);
				// Destruction des Spell tombés à zéro star
				if (spell0.getStar() <=0) {
					spells.remove(spell0);
				}
				if (spell1.getStar() <=0) {
					spells.remove(spell1);
				}
			}
		}
		for (int i = 0; i < spells.size() ; i++) {	// Collision entre Spell et Character
			Spell spell = spells.get(i);
			if (spell.getSide()) {	// Si le Spell va vers la droite
				if (spell.getX() + spell.getSpriteWidth() >= characters[1].getX()) {	// Si le Spell est plus à droite que le Character
					spellTouchCharacter(1, spell);
				}
			} else {	// Si le Spell va vers la gauche
				if (characters[0].getX() + characters[0].getSpriteWidth() >= spell.getX()) {	// Si le Spell est plus à gauche que le Character
					spellTouchCharacter(0, spell);
				}
			}
		}
	}

	public void spellTouchCharacter(int i, Spell spell) {
		characters[i].takeDamage(spell.getDamageToDo());	// Inflige les dégâts au Character
		spells.remove(spell);	//Retire le Spell
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		context.setFont(this.titleFont);
		context.setColor(this.titleColor);
		context.drawString(this.title, this.titleX, this.titleY);
		context.setFont(this.subTitleFont);
		context.setColor(this.subTitleColor);
		context.drawString(this.subTitle, this.subTitleX, this.subTitleY);
		for (Character character: this.characters) {
			character.render(container, game, context);
		}

		for (Spell spell : spells) {
			spell.render(container, game, context);
		}
	}

	public void keyPressed(int key, char value) {
		for (Character character: this.characters) {
			character.keyPressed(key, value);
		}
	}

	public void start(GameContainer container, Subject subject, int index) {
		container.getInput().enableKeyRepeat();
		// Partie Character et Spell :
//		int side = Duel.RNG.nextInt(2);
		int side = 1;
		boolean sideBoolean = side==1? true : false;
		this.characters[side] = new Player("JOUEUR",1000,this, sideBoolean);
		this.characters[1 - side] = new AI("Deep Neural Network", 1000,this, !sideBoolean);
		this.spells = new ArrayList<>(2);

		//Partie statistiques :
		this.failures.clear();
		this.durations.clear();

		// Partie affichage chapitre et sujet :
		this.subject = subject;
		this.chapter = subject.getChapter(index);
		this.title = "Subject: " + this.subject.getName();
		this.subTitle = "Chapter: " + this.chapter.getName();
		this.titleFont = Duel.font;
		this.titleColor = Color.white;
		this.titleX = 640 - this.titleFont.getWidth(title) / 2;
		this.titleY = 60 - this.titleFont.getHeight(title) / 2;
		this.subTitleFont = Duel.font;
		this.subTitleColor = Color.white;
		this.subTitleX = 640 - this.subTitleFont.getWidth(subTitle) / 2;
		this.subTitleY = 100 - this.subTitleFont.getHeight(subTitle) / 2;

		for(Character character: characters) { // TODO: remplacer cette attribution de base
			character.setexercise(new Exercise("Conjugate \"have\" at...", "has"));
		}
	}

	public void end(GameContainer container) {
		container.getInput().enableKeyRepeat();
	}

	public void characterDied(boolean side) {
		//TODO : indiquer la fin du duel
	}

	public void launchSpell(int x, int y, boolean side, int star, int damage) {
		Spell spell = new Spell(x, y, side, star, damage);
		int index = 0;		// Index par défaut (s'il n'y a pas encore de Spell dans spells)
		if (spells.size() > 0) {	// Il y a déjà un Spell
			index = side? 1 : 0;	// Alors on calcul l'index où mettre le Spell en fonction de son side
		}
		spells.add(index, spell);
	}

}
