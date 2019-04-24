package english;

import java.util.List;

public class Statistics {

	private double failureMean;
	private double failureVariance;
	private double durationMean;
	private double durationVariance;

	public Statistics() {
		this(0, 0, 0, 0);
	}

	public Statistics(double failureMean, double failureVariance, double durationMean, double durationVariance) {
		this.failureMean = failureMean;
		this.failureVariance = failureVariance;
		this.durationMean = durationMean;
		this.durationVariance = durationVariance;
	}

	public Statistics(List<Integer> durations/*, ... */) {
		 // TODO calculer les statistiques
	}

	public double getFailureMean() {
		return this.failureMean;
	}

	public double getFailureVariance() {
		return this.failureVariance;
	}

	public double getDurationMean() {
		return this.durationMean;
	}

	public double getDurationVariance() {
		return this.durationVariance;
	}

}
