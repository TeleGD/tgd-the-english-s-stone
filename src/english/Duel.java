package english;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class Duel extends BasicGameState {


	private int ID;
	private float aspectRatio;   // Ratio multiplicatif à appliquer aux positionnements pour tenir compte de la taille réelle de la fenêtre de jeu
	private Subject subject;
	private Chapter chapter;
	private Character[] characters;
	private List<Integer> failures;
	private List<Integer> durations;
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
	private Random RNG;


	public Duel(int ID) {
		this.ID = ID;
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

		//Collisions :
		int distanceBetweenCharacters = Math.abs(this.characters[0].getX() - this.characters[1].getX()) - this.characters[0].getSpriteWidth();  // Distance que doit parcourir un spell pour toucher l'autre personnage
		List<Spell> spellsToRemove1 = new ArrayList<>();
		List<Spell> spellsToRemove2 = new ArrayList<>();
		List<List<Spell>> removeLists = new ArrayList<>();
		removeLists.add(spellsToRemove1);
		removeLists.add(spellsToRemove2);

		for (int i = 0; i < 2; i++){
			Character character1 = this.characters[i];
			Character character2 = this.characters[1-i];

			for (Spell spell1 : character1.getSpells()){
				// Collision avec l'autre personnage :
				if (Math.abs((spell1.getX() + spell1.getSpriteWidth()/2) - (character1.getX() + character1.getSpriteWidth()/2)) >= distanceBetweenCharacters ) {	// Si le Spell a parcourut une plus grande distance que celle séparant les personnages : il a touché l'autre personnage
					spellTouchCharacter(1-i, spell1);
					removeLists.get(i).add(spell1);
				}

				// Collision avec un sort de l'adversaire :
				for (Spell spell2 : character2.getSpells()){
					if (Math.abs(spell1.getX() - spell2.getX())  <=  spell1.getSpriteWidth()) {	// S'il y a collision entre Spell
						//Collision entre Spell
						int starOfSpell1 =spell1.getStar();
						spell1.collideWithOtherSpell(spell2.getStar());
						spell2.collideWithOtherSpell(starOfSpell1);
						// Destruction des Spell tombés à zéro star
						if (spell1.getStar() <=0) {
							spellsToRemove1.add(spell1);
						}
						if (spell2.getStar() <=0) {
							spellsToRemove2.add(spell2);
						}
					}
				}
			}
		}
		characters[0].getSpells().removeAll(spellsToRemove1);
		characters[1].getSpells().removeAll(spellsToRemove2);
	}

	public void spellTouchCharacter(int i, Spell spell) {
		characters[i].takeDamage(spell.getDamageToDo());	// Inflige les dégâts au Character
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		context.setFont(this.titleFont);
		context.setColor(this.titleColor);
		context.drawString(this.title, this.titleX * this.aspectRatio, this.titleY * this.aspectRatio);

		context.setFont(this.subTitleFont);
		context.setColor(this.subTitleColor);
		context.drawString(this.subTitle, this.subTitleX * this.aspectRatio, this.subTitleY * this.aspectRatio);

		for (Character character: this.characters) {
			character.render(container, game, context);
		}

	}

	public void keyPressed(int key, char value) {
		for (Character character: this.characters) {
			character.keyPressed(key, value);
		}
	}

	public void start(GameContainer container, Subject subject, int index) {
		container.getInput().enableKeyRepeat();
		this.aspectRatio = Math.min(container.getWidth() / 1280f, container.getHeight() / 720f);
		//Partie statistiques :
		this.failures = new ArrayList<Integer>();
		this.durations = new ArrayList<Integer>();
		this.RNG = new Random();
		// Partie Character et Spell :
//		int side = Duel.RNG.nextInt(2);
		this.characters = new Character[2];
		int side = 1;
		boolean sideBoolean = side==1? true : false;
		this.characters[side] = new Player(this.aspectRatio, "JOUEUR",1000,this, sideBoolean);
		this.characters[1 - side] = new AI(this.aspectRatio, "Deep Neural Network", 1000,this, !sideBoolean);
		Font font = AppLoader.loadFont("/fonts/press-start-2p.ttf", java.awt.Font.BOLD, (int) (40 * this.aspectRatio));

		// Partie affichage chapitre et sujet :
		this.subject = subject;
		this.chapter = subject.getChapter(index);
		this.title = "Subject: " + this.subject.getName();
		this.subTitle = "Chapter: " + this.chapter.getName();
		this.titleFont = font;
		this.titleColor = Color.white;
		this.titleX = 640 - (int) (this.titleFont.getWidth(title) / this.aspectRatio) / 2;
		this.titleY = 60 - (int) (this.titleFont.getHeight(title) / this.aspectRatio) / 2;
		this.subTitleFont = font;
		this.subTitleColor = Color.white;
		this.subTitleX = 640 - (int) (this.subTitleFont.getWidth(subTitle) / this.aspectRatio) / 2;
		this.subTitleY = 100 - (int) (this.subTitleFont.getHeight(subTitle) / this.aspectRatio) / 2;

		for(Character character: characters) { // TODO: remplacer cette attribution de base
			character.setexercise(new Exercise("Conjugate \"have\" at...", "has"));
		}
	}

	public void end(GameContainer container) {
		container.getInput().disableKeyRepeat();
	}

	public void characterDied(boolean side) {
		//TODO : indiquer la fin du duel
		System.out.println("Dueliste n°" + side + "est mort !");
	}

}
