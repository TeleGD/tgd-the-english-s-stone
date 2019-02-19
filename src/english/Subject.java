package english;

import java.util.List;

public class Subject {

	private String name;
	private String description;
	private List<Chapter> chapters;

	public Subject(String name, String description, List<Chapter> chapters) {
		this.name = name;
		this.description = description;
		this.chapters = chapters;
	}

	public String getName() {
		return this.name;
	}

	public Chapter getChapter(int index) {
		return index >= 0 && index < this.chapters.size() ? this.chapters.get(index) : null;
	}

	public int getChapterCount() {
		return this.chapters.size();
	}

}
