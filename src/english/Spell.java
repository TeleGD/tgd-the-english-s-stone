package english;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Spell extends Entity {

	private int star;
	private int damage;

	public Spell(int x, int y, boolean side, int star, int damage) {
		super("/images/spell/Arcane_Effect_4.png",100,100, x, y, (side? -1 : 1)* 5 );
		this.star = star;
		this.damage = damage;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		//TODO : check de colision avec un Character ou un autre Spell
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
