package games.theEnglishSStone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import app.AppLoader;

public class Loader {

	static public List<Subject> restore() {
		List<String> subjectDirectories = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new StringReader(AppLoader.loadData("/data/theEnglishSStone/.txt")));
			String line;
			while ((line = reader.readLine()) != null) {
				subjectDirectories.add(line);
			}
			reader.close();
		} catch (IOException error) {}
		List<Subject> subjects = new ArrayList<Subject>();
		for (String subjectDirectory: subjectDirectories) {
			String subjectName = "No name";
			List<Chapter> chapters = new ArrayList<Chapter>();
			try {
				BufferedReader reader = new BufferedReader(new StringReader(AppLoader.loadData("/data/theEnglishSStone/" + subjectDirectory + ".txt")));
				String line;
				if ((line = reader.readLine()) != null) {
					subjectName = line;
				}
				reader.close();
			} catch (IOException error) {}
			List<String> chapterFiles = new ArrayList<String>();
			try {
				BufferedReader reader = new BufferedReader(new StringReader(AppLoader.loadData("/data/theEnglishSStone/" + subjectDirectory + "/.txt")));
				String line;
				while ((line = reader.readLine()) != null) {
					chapterFiles.add(line);
				}
				reader.close();
			} catch (IOException error) {}
			for (String chapterFile: chapterFiles) {
				String chapterName = "No name";
				Statistics statistics = new Statistics();
				List<Exercise> exercices = new ArrayList<Exercise>();
				try {
					BufferedReader reader = new BufferedReader(new StringReader(AppLoader.loadData("/data/theEnglishSStone/" + subjectDirectory + "/" + chapterFile + ".txt")));
					String line1;
					String line2;
					if ((line1 = reader.readLine()) != null) {
						chapterName = line1;
					}
					while ((line1 = reader.readLine()) != null && (line2 = reader.readLine()) != null) {
						exercices.add(new Exercise(line1, line2));
					}
					reader.close();
				} catch (IOException error) {}
				try {
					BufferedReader reader = new BufferedReader(new StringReader(AppLoader.restoreData("/theEnglishSStone/" + subjectDirectory + "/" + chapterFile + ".txt")));
					String line;
					double[] doubles = new double[]{
						1,
						1,
						5000,
						3000
					};
					int i = 0;
					while ((line = reader.readLine()) != null) {
						if (i >= 4) {
							break;
						}
						try {
							doubles[i++] = Double.parseDouble(line);
						} catch (NumberFormatException error) {}
					}
					statistics = new Statistics(doubles[0], doubles[1], doubles[2], doubles[3]);
					reader.close();
				} catch (IOException error) {}
				chapters.add(new Chapter(chapterFile, chapterName, statistics, exercices));
			}
			subjects.add(new Subject(subjectDirectory, subjectName, chapters));
		}
		return subjects;
	}

	static public void save(List<Subject> subjects) {
		for (Subject subject: subjects) {
			String subjectDirectory = subject.getFilename();
			for (int i = 0, li = subject.getChapterCount(); i < li; ++i) {
				Chapter chapter = subject.getChapter(i);
				try {
					StringWriter writer = new StringWriter();
					Statistics statistics = chapter.getStatistics();
					String[] strings = new String[]{
						Double.toString(statistics.getFailureMean()),
						Double.toString(statistics.getFailureDeviation()),
						Double.toString(statistics.getDurationMean()),
						Double.toString(statistics.getDurationDeviation())
					};
					for (String string: strings) {
						writer.write(string + "\n");
					}
					AppLoader.saveData("/theEnglishSStone/" + subjectDirectory + "/" + chapter.getFilename() + ".txt", writer.toString());
					writer.close();
				} catch (IOException error) {}
			}
		}
	}

}
