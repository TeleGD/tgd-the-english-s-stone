package theEnglishSStone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Loader {

	static private String home;

	static {
		Loader.home = System.getProperty ("user.home") + File.separator + ".the-english-s-stone";
	}

	static public List<Subject> restore() {
		File homeFile = new File(home);
		homeFile.mkdirs();
		String[] subjectDirectories = homeFile.list((directory, subfile) -> new File(directory, subfile).isDirectory());
		List<Subject> subjects = new ArrayList<Subject>();
		for (String subjectDirectory: subjectDirectories) {
			String subjectName = "No name";
			List<Chapter> chapters = new ArrayList<Chapter>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(home + File.separator + subjectDirectory + ".txt"));
				String line;
				if ((line = reader.readLine ()) != null) {
					subjectName = line;
				}
				reader.close ();
			} catch (IOException error) {}
			String[] chapterFiles = new File(homeFile, subjectDirectory).list ((directory, subfile) -> new File(directory, subfile).isFile());
			for (String chapterFile: chapterFiles) {
				String chapterName = "No name";
				Statistics statistics = new Statistics();
				List<Exercise> exercices = new ArrayList<Exercise>();
				try {
					BufferedReader reader = new BufferedReader(new FileReader(home + File.separator + subjectDirectory + File.separator + chapterFile));
					String line1;
					String line2;
					if ((line1 = reader.readLine ()) != null) {
						chapterName = line1;
					}
					if ((line1 = reader.readLine ()) != null) {
						double[] doubles = new double[]{
							1,
							1,
							5000,
							3000
						};
						int i = 0;
						for (String string: line1.split("|")) {
							if (i >= 4) {
								break;
							}
							try {
								doubles[i++] = Double.parseDouble(string);
							} catch (NumberFormatException error) {}
						}
						statistics = new Statistics(doubles[0], doubles[1], doubles[2], doubles[3]);
					}
					while ((line1 = reader.readLine ()) != null && (line2 = reader.readLine ()) != null) {
						exercices.add(new Exercise(line1, line2));
					}
					reader.close ();
				} catch (IOException error) {}
				chapters.add(new Chapter(chapterFile, chapterName, statistics, exercices));
			}
			subjects.add(new Subject(subjectDirectory, subjectName, chapters));
		}
		return subjects;
	}

	static public void save(List<Subject> subjects) {
		File homeFile = new File(home);
		homeFile.mkdirs();
		for (Subject subject: subjects) {
			String subjectDirectory = subject.getFilename();
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(home + File.separator + subjectDirectory + ".txt"));
				writer.write(subject.getName() + "\n");
				writer.close ();
			} catch (IOException error) {}
			for (int i = 0, li = subject.getChapterCount(); i < li; ++i) {
				Chapter chapter = subject.getChapter(i);
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(home + File.separator + subjectDirectory + File.separator + chapter.getFilename()));
					writer.write(chapter.getName() + "\n");
					Statistics statistics = chapter.getStatistics();
					String[] strings = new String[]{
						Double.toString(statistics.getFailureMean()),
						Double.toString(statistics.getFailureDeviation()),
						Double.toString(statistics.getDurationMean()),
						Double.toString(statistics.getDurationDeviation())
					};
					writer.write(String.join("|", strings) + "\n");
					for (int j = 0, lj = chapter.getExerciseCount(); j < lj; ++j) {
						Exercise exercise = chapter.getExercise(j);
						writer.write(exercise.getQuestion() + "\n" + exercise.getAnswer() + "\n");
					}
					writer.close ();
				} catch (IOException error) {}
			}
		}
	}

}
