package english;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

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

	public Entity(String spritePath, int x, int y, int dx) {
		this.setSprite(AppLoader.loadPicture(spritePath));
		dy = 0;
		this.x = x;
		this.y = y;
		this.dx = dx;
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
				this.sprite,
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

}
