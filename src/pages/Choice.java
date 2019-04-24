package pages;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppMenu;
import app.elements.MenuItem;

import english.Duel;
import english.Subject;

public class Choice extends AppMenu {

	private List<Subject> subjects;

	public Choice(int ID, StateBasedGame game, List<Subject> subjects) {
		super(ID);
		this.subjects = subjects;
		int i = 3;
		for (Subject subject: this.subjects) {
			game.addState(new AppMenu(i++) {

				public void init(GameContainer container, StateBasedGame game) {
					super.initSize(container, game, 600, 400);
					super.init(container, game);
					this.setTitle("The English's stone");
					this.setSubtitle(subject.getName());
					this.setHint("SELECT A CHAPTER");
					List<MenuItem> menuItems = new ArrayList<MenuItem>();
					for (int i = 0, li = subject.getChapterCount(); i < li; ++i) {
						int j = i;
						menuItems.add(new MenuItem(subject.getChapter(j).getName()) {

							public void itemSelected() {
								if (subject.getChapter(j).getExerciseCount() > 0) {
									Duel duel = (Duel) game.getState(1);
									duel.start(container, subject, j);
									game.enterState(1, new FadeOutTransition(), new FadeInTransition());
								} else {
									System.out.println("No exercise available");
								}
							}

						});
					}
					menuItems.add(new MenuItem("Back") {
						public void itemSelected() {
							game.enterState(2, new FadeOutTransition(), new FadeInTransition());
						}
					});
					this.setMenu(menuItems);
				}

				public void update(GameContainer container, StateBasedGame game, int delta) {
					super.update(container, game, delta);
				}

			});
		}
	}

	public void init(GameContainer container, StateBasedGame game) {
		super.initSize(container, game, 600, 400);
		super.init(container, game);
		this.setTitle("The English's stone");
		this.setSubtitle("Subjects");
		this.setHint("SELECT A SUBJECT");
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		int i = 3;
		for (Subject subject: this.subjects) {
			int j = i;
			menuItems.add(new MenuItem(subject.getName()) {

				public void itemSelected() {
					game.enterState(j, new FadeOutTransition(), new FadeInTransition());
				}

			});
		}
		menuItems.add(new MenuItem("Back") {
			public void itemSelected() {
				game.enterState(0, new FadeOutTransition(), new FadeInTransition());
			}
		});
		this.setMenu(menuItems);
	}

}
