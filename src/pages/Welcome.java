package pages;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppFont;
import app.AppLoader;
import app.AppPage;

public class Welcome extends AppPage {

	private Image logo;

	private boolean logoVisibility;

	protected int logoBoxWidth;
	protected int logoBoxHeight;
	protected int logoBoxX;
	protected int logoBoxY;

	private int logoWidth;
	private int logoHeight;
	private int logoX;
	private int logoY;

	private int logoNaturalWidth;
	private int logoNaturalHeight;

	private Font titleFont;
	private Color titleColor;
	private String title;

	public Welcome(int ID) {
		super(ID);
	}

	public void init(GameContainer container, StateBasedGame game) {
		super.initSize(container, game, 600, 400);
		super.init(container, game);

		this.logoBoxX = this.contentX;
		this.logoBoxY = this.subtitleBoxY + this.subtitleBoxHeight + AppPage.gap;
		this.logoBoxWidth = this.contentWidth;
		this.logoBoxHeight = this.hintBoxY - this.logoBoxY - AppPage.gap;

		this.logoVisibility = true;

		this.titleVisibility = false;
		this.subtitleVisibility = false;
		this.hintBlink = true;

		this.setHint("PRESS [START]");
		this.setLogo(AppLoader.loadPicture("/images/logo.png"));

		this.titleFont = AppLoader.loadFont("/fonts/henny-penny.ttf", AppFont.BOLD, 40);
		this.titleColor = Color.white;
		this.title = "The English's stone";
	}

	public void update(GameContainer container, StateBasedGame game, int  delta) {
		super.update(container, game, delta);
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			container.exit();
		} else if (input.isKeyDown(Input.KEY_ENTER)) {
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
		// this.renderLogo(container, game, context);
		context.setFont(this.titleFont);
		context.setColor(this.titleColor);
		context.drawString(this.title, this.logoBoxX + (this.logoBoxWidth - this.titleFont.getWidth(this.title)) / 2, this.logoBoxY);
	}

	private void renderLogo(GameContainer container, StateBasedGame game, Graphics context) {
		if (this.logoVisibility) {
			context.drawImage(
				this.logo,
				this.logoX,
				this.logoY,
				this.logoX + this.logoWidth,
				this.logoY + this.logoHeight,
				0,
				0,
				this.logoNaturalWidth,
				this.logoNaturalHeight
			);
		}
	}

	public void setLogo(Image logo) {
		this.logo = logo.copy();
		this.logoNaturalWidth = logo.getWidth();
		this.logoNaturalHeight = logo.getHeight();
		this.logoWidth = Math.min(Math.max(this.logoBoxWidth, 0), this.logoNaturalWidth);
		this.logoHeight = Math.min(Math.max(this.logoBoxHeight, 0), this.logoNaturalHeight);
		int a = this.logoWidth * this.logoNaturalHeight;
		int b = this.logoNaturalWidth * this.logoHeight;
		if (a < b) {
			this.logoHeight = this.logoNaturalHeight * this.logoWidth / this.logoNaturalWidth;
		} else if (b < a) {
			this.logoWidth = this.logoNaturalWidth * this.logoHeight / this.logoNaturalHeight;
		}
		this.logoX = this.logoBoxX + (this.logoBoxWidth - this.logoWidth) / 2;
		this.logoY = this.logoBoxY + (this.logoBoxHeight - this.logoHeight) / 2;
	}

	public Image getLogo() {
		return logo.copy();
	}

}
