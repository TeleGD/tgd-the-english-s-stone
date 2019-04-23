package pages;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppMenu;
import app.elements.MenuItem;

import english.Chapter;
import english.Duel;
import english.Exercise;
import english.Statistics;
import english.Subject;

public class Choice extends AppMenu {

	public Choice(int ID) {
		super(ID);
	}

	public void init(GameContainer container, StateBasedGame game) {
		super.initSize(container, game, 600, 400);
		super.init(container, game);
		this.setSubtitle("Sans sous-titre");
		this.setMenu(Arrays.asList(new MenuItem[] {
			new MenuItem("English project") {
				public void itemSelected() {
					Exercise exercise = new Exercise("Conjugate \"be\" at...", "is");
					List<Exercise> exercises = new ArrayList<Exercise>();
					exercises.add(exercise);
					Chapter chapter = new Chapter("Irregular verbs", "", new Statistics(), exercises);
					List<Chapter> chapters = new ArrayList<Chapter>();
					chapters.add(chapter);
					Subject subject = new Subject("Conjugaison", "", chapters);
					Duel duel = (Duel) game.getState(2);
					duel.start(container, subject, 0);
					game.enterState(2, new FadeOutTransition(), new FadeInTransition());
				}
			},
			new MenuItem("Retour") {
				public void itemSelected() {
					game.enterState(0, new FadeOutTransition(), new FadeInTransition());
				}
			}
		}));
		this.setTitle("Choix");
		this.setHint("SELECT A GAME");
	}

}
