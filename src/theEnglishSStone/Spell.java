package theEnglishSStone;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class Spell extends Entity {

	private int star;
	private int damage;

	public Spell(float aspectRatio, int x, int y, boolean side, int star, int damage) {
		super(aspectRatio, "/images/spell/spritesheetSpell.png",100,100,124,108,3, x, y, (side? -1 : 1)* 10, side );
		this.star = star;
		this.damage = damage;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		//TODO : check de colision avec un Character ou un autre Spell
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context, star - 1);
	}

	@Override
	public void loadAnimations(SpriteSheet spriteSheet) {
		for (int i = 0; i < 3; i++) {
			loadAnimation(spriteSheet,0,6,i, i);
		}
	}

	public int getDamageToDo() {
		return (int) (damage * star) /3;
	}

	/**
	 * Retourne le nombre de star restant après différence avec le nombre de star de spell
	 * @param starOfOtherSpell
	 * @return
	 */
	public int collideWithOtherSpell(int starOfOtherSpell) {

		star -= starOfOtherSpell;
		if (star <= 0) {
			star = 0;
		}
		return star;
	}

	public int getStar() {
		return star;
	}

}
