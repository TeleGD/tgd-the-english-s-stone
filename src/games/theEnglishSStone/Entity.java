package games.theEnglishSStone;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public abstract class Entity {

	private float aspectRatio;
	private int x;
	private int y;
	private int dx;
	private int dy;
	private Animation[] animations; // Tableau d'animations différences (qui sont des ensembles de sprites)
	private int spriteWidth;
	private int spriteHeight;
	private int spriteNaturalWidth;
	private int spriteNaturalHeight;
	protected boolean side; // true : Regarde vers la droite, false : Regarde vers la gauche

	/**
	 * @param spritePath
	 * @param spriteWidth largeur d'un sprite à l'affichage
	 * @param spriteHeight hauteur d'un sprite à l'affichage
	 * @param spriteNaturalWidth largeur d'un sprite dans le spriteSheet des ressources du jeu
	 * @param spriteNaturalHeight hauteur d'un sprite dans le spriteSheet des ressources du jeu
	 * @param x
	 * @param y
	 * @param dx
	 * @param side
	 */
	public Entity(float aspectRatio, String spritePath, int spriteWidth, int spriteHeight, int spriteNaturalWidth, int spriteNaturalHeight, int nbAnimLines, int x, int y, int dx, boolean side) {
		this.aspectRatio = aspectRatio;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		dy = 0;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.side = side;

		// Chargements des sprites dans les animations :
		SpriteSheet spriteSheet = null;
		Image spriteImage = AppLoader.loadPicture(spritePath);
		spriteSheet = new SpriteSheet(spriteImage, spriteNaturalWidth, spriteNaturalHeight);

		animations = new Animation[nbAnimLines];
		loadAnimations(spriteSheet);

	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		x += dx;
		y += dy;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context, int animLine) {
		renderSprite(container, game, context, animLine);   // Affichage du sprite de Entity
	}

	public void renderSprite(GameContainer container, StateBasedGame game, Graphics context, int animLine) {
		context.drawAnimation(animations[animLine], this.x * this.aspectRatio, this.y * this.aspectRatio);    //TODO : vérifier qu'il n'y a pas de décalage x y
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

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public boolean getSide() {
		return side;
	}

	/**
	 * Charge une ligne de spritesheet dans une Animation
	 * @param spriteSheet
	 * @param startX
	 * @param endX
	 * @param animLineToLoad
	 * @param animLineToStore
	 * @return
	 */
	public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int animLineToLoad, int animLineToStore) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, animLineToLoad).getScaledCopy((int) (spriteWidth * this.aspectRatio), (int) (spriteHeight * this.aspectRatio)).getFlippedCopy(side, false), 100);
		}
		animations[animLineToStore] = animation;
	}

	/**
	 * Charge les bonnes animations en fonction de la sous-classe de Entity
	 */
	public abstract void loadAnimations(SpriteSheet spriteSheet);

}
