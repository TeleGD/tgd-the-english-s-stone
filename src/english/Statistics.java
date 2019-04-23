package english;

import java.util.List;

public class Statistics {

	private int failureMean;
	private int failureVariance;
	private int durationMean;
	private int durationVariance;

	public Statistics() {
		this.failureMean = 0;
		this.failureVariance = 0;
		this.durationMean = 0;
		this.durationVariance = 0;
	}

	public Statistics(List<Integer> durations/*, ... */) {
		 // TODO calculer les statistiques
	}

}
