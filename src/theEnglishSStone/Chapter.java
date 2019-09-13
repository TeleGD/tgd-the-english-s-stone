package theEnglishSStone;

import java.util.List;

public class Chapter {

	private String filename;
	private String name;
	private Statistics statistics;
	private List<Exercise> exercises;

	public Chapter(String filename, String name, Statistics statistics, List<Exercise> exercises) {
		this.filename = filename;
		this.name = name;
		this.statistics = statistics;
		this.exercises = exercises;
	}

	public String getFilename() {
		return this.filename;
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
