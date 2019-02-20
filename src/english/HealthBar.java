package english;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class HealthBar {
	int maxHP;
	int currentHP;
	int x0;
	int realX;
	int y0;
	int width;
	int height;
	boolean flipped;
	int xText;
	int yText;
	int textOffset; // Distance entre le texte et le x0

	public HealthBar(int x0, int y0, int width, int height, int maxHP, boolean flipped) {
		// x0  et y0 sont les coordonnées du point d'origine de la barre d'HP : là où est situé le "0 HP"
		this.x0 = x0;
		this.y0 = y0;
		this.width = width;
		this.height = height;
		this.maxHP = maxHP;
		this.flipped = flipped;

		this.textOffset = 100;
		this.yText = y0 - height/3;


		this.currentHP = maxHP;

		if (flipped){
			realX = x0 - width; // Décalage de l'origine en cas de symétrie horizontale
			this.xText = x0 - textOffset;
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
		context.fillRect(realX, y0, width, height);

		// Partie verte de la barre de vie
		context.setColor(Color.green);
		context.fillRect(x0Bar, y0, (int)(width*(double)(currentHP)/maxHP), height);

		// Affichage textuel des HP :
		context.setColor(Color.white);
		context.drawString("HP : "+ currentHP +" / " + maxHP, xText, yText);
	}
}
