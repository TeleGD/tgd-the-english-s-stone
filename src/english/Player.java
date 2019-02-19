package english;

import org.newdawn.slick.GameContainer; import org.newdawn.slick.state.StateBasedGame;

public class Player extends Character {
	
	public Player(String name, int HPmax, Duel duel, boolean side) {
		super("/images/characters/YoungWizard1.png", name, HPmax, duel, side);
		// TODO Auto-generated constructor stub
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {}

}
