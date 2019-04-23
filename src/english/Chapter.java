package english;

import java.util.List;

public class Chapter {

	private String name;
	private String description;
	private Statistics statistics;
	private List<Exercise> exercises;

	public Chapter(String name, String description, Statistics statistics, List<Exercise> exercises) {
		this.name = name;
		this.description = description;
		this.statistics = statistics;
		this.exercises = exercises;
	}

	public String getName() {
		return this.name;
	}

	public Exercise getExercise(int index) {
		return index >= 0 && index < this.exercises.size() ? this.exercises.get(index) : null;
	}

	public int getExerciseCount() {
		return this.exercises.size();
	}

	public Statistics getStatistics() {
		return this.statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

}
