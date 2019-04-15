package english;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class AI extends Character {
	
	private Statistics statistics;

	public AI(String name, int HPmax, Duel duel, boolean side) {
		super("/images/characters/FierceWizard1.png", name, HPmax, duel, side);
		// TODO Auto-generated constructor stub
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		Input input = container.getInput ();
		if (input.isKeyPressed (Input.KEY_M)) { //TODO : retirer cet Input de debug
			launchSpell();
			System.out.println("Je lance un sort !");
		}
	}

}
