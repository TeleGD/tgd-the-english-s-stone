package english;

import java.util.List;

public class Subject {

	private String filename;
	private String name;
	private List<Chapter> chapters;

	public Subject(String filename, String name, List<Chapter> chapters) {
		this.filename = filename;
		this.name = name;
		this.chapters = chapters;
	}

	public String getFilename() {
		return this.filename;
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
