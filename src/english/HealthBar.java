package english;

import app.AppLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class HealthBar {

	private float aspectRatio;
	private int maxHP;
	private int currentHP;
	private int x0;
	private int realX;
	private int y0;
	private int width;
	private int height;
	private boolean flipped;
	private int xText;
	private int yText;
	private int textOffset; // Distance entre le texte et le x0
	private Font textFont;

	public HealthBar(float aspectRatio, int x0, int y0, int width, int height, int maxHP, boolean flipped) {
		// x0  et y0 sont les coordonnées du point d'origine de la barre d'HP : là où est situé le "0 HP"
		this.aspectRatio = aspectRatio;
		this.x0 = x0;
		this.y0 = y0;
		this.width = width;
		this.height = height;
		this.maxHP = maxHP;
		this.flipped = flipped;
		this.textFont = AppLoader.loadFont("/fonts/vt323.ttf", java.awt.Font.BOLD, (int) (24 * this.aspectRatio));

		this.textOffset = 40;
		this.yText = y0 + height/2 - (int) (textFont.getHeight("HP") / this.aspectRatio)/2;


		this.currentHP = maxHP;

		if (flipped){
			realX = x0 - width; // Décalage de l'origine en cas de symétrie horizontale
			this.xText = x0 - textOffset - (int) (textFont.getWidth("HP : " + maxHP + " / " + maxHP) / this.aspectRatio);
		} else{
			this.xText = x0 + textOffset;
		}
	}

	public void setCurrentHP(int currentHP){
		this.currentHP = currentHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		int x0Bar = x0; // Origine de la barre verte des HP
		if (flipped){
			x0Bar -=  (int)(width*(double)(currentHP)/maxHP);
		}
		// Fond de la barre de vie :
		context.setColor(Color.gray);
		context.fillRect(realX * this.aspectRatio, y0 * this.aspectRatio, width * this.aspectRatio, height  * this.aspectRatio);

		// Partie verte de la barre de vie
		context.setColor(Color.green);
		context.fillRect(x0Bar * this.aspectRatio, y0 * this.aspectRatio, (int)(width*(double)(currentHP)/maxHP) * this.aspectRatio, height * this.aspectRatio);

		// Affichage textuel des HP :
		context.setFont(this.textFont);
		context.setColor(Color.white);
		context.drawString("HP : "+ currentHP +" / " + maxHP, xText * this.aspectRatio, yText * this.aspectRatio);
	}
}
