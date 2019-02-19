package english;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Spell extends Entity {

	private int star;
	private int damage;

	public Spell(int x, int y, boolean side, int star, int damage) {
		super("images/spell/Arcane_Effect_4.png", x, y, (side? -1 : 1)* 10 );
		this.star = star;
		this.damage = damage;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		//TODO : check de colision avec un Character ou un autre Spell
	}

}
