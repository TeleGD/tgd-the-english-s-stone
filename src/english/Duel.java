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
		for (Character character: this.characters) {
			character.update(container, game, delta);
		}
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
	}

	public void keyPressed(int key, char value) {
		for (Character character: this.characters) {
			character.keyPressed(key, value);
		}
	}

	public void start(Subject subject, int index) {
		this.subject = subject;
		this.chapter = subject.getChapter(index);
		int side = Duel.RNG.nextInt(2);
		this.characters[side] = new Player();
		this.characters[1 - side] = new AI();
		this.failures.clear();
		this.durations.clear();
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
	}

	public void end() {

	}

}
