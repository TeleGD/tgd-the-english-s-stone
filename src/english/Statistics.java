package english;

import java.util.List;

public class Statistics {

	private double failureMean;
	private double failureDeviation;
	private double durationMean;
	private double durationDeviation;

	public Statistics() {
		this(1, 1, 5000, 3000);
	}

	public Statistics(double failureMean, double failureDeviation, double durationMean, double durationDeviation) {
		this.failureMean = failureMean;
		this.failureDeviation = failureDeviation;
		this.durationMean = durationMean;
		this.durationDeviation = durationDeviation;
	}

	public Statistics(List<Integer> failures, List<Integer> durations) {
		if (failures.size() > 0) {
			this.failureMean = 0;
			this.failureDeviation = 0;
			for (int failure: failures) {
				this.failureMean += failure;
				this.failureDeviation += failure * failure;
			}
			this.failureMean /= failures.size();
			this.failureDeviation = Math.sqrt(this.failureDeviation / failures.size() - this.failureMean * this.failureMean);
			if (Double.isNaN(this.failureDeviation)) {
				this.failureDeviation = 1;
			}
		} else {
			this.failureMean = 1;
			this.failureDeviation = 1;
		}
		if (durations.size() > 0) {
			this.durationMean = 0;
			this.durationDeviation = 0;
			for (int duration: durations) {
				this.durationMean += duration;
				this.durationDeviation += duration * duration;
			}
			this.durationMean /= durations.size();
			this.durationDeviation = Math.sqrt(this.durationDeviation / durations.size() - this.durationMean * this.durationMean);
			if (Double.isNaN(this.durationDeviation)) {
				this.durationDeviation = 3000;
			}
		} else {
			this.durationMean = 5000;
			this.durationDeviation = 3000;
		}
	}

	public double getFailureMean() {
		return this.failureMean;
	}

	public double getFailureDeviation() {
		return this.failureDeviation;
	}

	public double getDurationMean() {
		return this.durationMean;
	}

	public double getDurationDeviation() {
		return this.durationDeviation;
	}

}
