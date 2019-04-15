package english;

public class Exercise  {

	private String question;
	private String answer;

	public Exercise(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public Object getQuestion() {
		return this.question;
	}

	public Object getAnswer() {
		return this.answer;
	}

}
