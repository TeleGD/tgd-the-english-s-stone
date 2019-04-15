package english;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class TextField {

	int x;
	int y;
	int width;
	int height;
	int cornerRadius;
	int borderWidth;
	int padding;
	int line;
	Font font;
	Color backgroundColor;
	Color borderColor;
	Color textColor;
	Color caretColor;
	String text;
	int caretBlinkPeriod;
	int caretBlinkCountdown;
	int caret;

	public TextField(int x, int y, int width, int height, int cornerRadius, int borderWidth) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.cornerRadius = cornerRadius;
		this.borderWidth = borderWidth;
		this.padding = (this.height - this.borderWidth * 2) / 10;
		this.line = (this.height - this.borderWidth * 2) - this.padding;
		this.font = AppLoader.loadFont("/fonts/vt323.ttf", java.awt.Font.PLAIN, this.line);
		this.backgroundColor = new Color(1f, 1f, 1f);
		this.borderColor = new Color(.6f, .6f, .6f);
		this.textColor = new Color(.2f, .2f, .2f);
		this.caretColor = new Color(1f, 0f, 0f);
		this.text = "";
		this.caretBlinkPeriod = 2000;
		this.caretBlinkCountdown = 0;
		this.caret = 0;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		this.caretBlinkCountdown = (this.caretBlinkCountdown + this.caretBlinkPeriod - delta) % this.caretBlinkPeriod;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		int borderWidth = this.borderWidth;
		int halfBorderWidth = borderWidth / 2;
		int padding = this.padding;
		context.setColor(this.backgroundColor);
		context.fillRoundRect(this.x + halfBorderWidth, this.y + halfBorderWidth, this.width - borderWidth, this.height - borderWidth, this.cornerRadius - halfBorderWidth);
		context.setColor(this.borderColor);
		context.setLineWidth(this.borderWidth);
		context.drawRoundRect(this.x + halfBorderWidth, this.y + halfBorderWidth, this.width - borderWidth, this.height - borderWidth, this.cornerRadius - halfBorderWidth);
		context.setColor(this.textColor);
		context.setFont(this.font);
		context.drawString(this.text, this.x + borderWidth + padding, this.y + borderWidth + padding);
		if (this.caretBlinkCountdown >= this.caretBlinkPeriod / 2) {
			int x = this.x + borderWidth + padding + this.font.getWidth(text.substring(0, this.caret));
			int y1 = this.y + borderWidth + padding;
			int y2 = y1 + this.line;
			context.setColor(caretColor);
			context.drawLine(x, y1, x, y2);
		}
	}

	public void setText(String text) {
		this.text = text;
		this.caretBlinkCountdown = 0;
	}

	public String getText() {
		return this.text;
	}

	public void setCaret(int caret) {
		this.caret = caret;
		this.caretBlinkCountdown = 0;
	}

	public int getCaret() {
		return this.caret;
	}

}
