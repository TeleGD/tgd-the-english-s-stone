package english;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import org.newdawn.slick.Image;

public class Entity {

	private int x;
	private int y;
	private int dx;
	private int dy;
	private Image sprite;
	private int spriteWidth;
	private int spriteHeight;
	private int spriteNaturalWidth;
	private int spriteNaturalHeight;
	protected boolean side; // true : Character à droite, false : Character à gauche
	
	public Entity(String spritePath, int x, int y, int dx) {
		this.setSprite(AppLoader.loadPicture(spritePath));
		dy = 0;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.side = (dx>=0);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		x += dx;
		y += dy;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		renderSprite(container, game, context);   // Affichage du sprite de Entity
	}
	
	public void renderSprite(GameContainer container, StateBasedGame game, Graphics context) {
		context.drawImage(
				this.sprite.getFlippedCopy(side, false),
				this.x,
				this.y,
				this.x + this.spriteWidth,
				this.y + this.spriteHeight,
				0,
				0,
				spriteNaturalWidth,
				spriteNaturalHeight
			);
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
		this.spriteNaturalHeight = sprite.getHeight();
		this.spriteNaturalWidth = sprite.getWidth();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDx() {
		return dx;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
