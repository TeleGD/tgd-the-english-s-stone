package english;

import org.newdawn.slick.SpriteSheet;

public class Star extends Entity{

	public Star(String spritePath, int spriteWidth, int spriteHeight, int spriteNaturalWidth, int spriteNaturalHeight, int x, int y, int dx){
		super(spritePath, spriteWidth, spriteHeight, spriteNaturalWidth, spriteNaturalHeight, 1, x, y, dx, false);
	}

	@Override
	public void loadAnimations(SpriteSheet spriteSheet) {
		loadAnimation(spriteSheet, 0,1,0, 0);
	}
}
